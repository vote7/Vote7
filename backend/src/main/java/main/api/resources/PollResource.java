package main.api.resources;

import main.api.data.SimpleResponse;
import main.api.data.answers.AnswerResponse;
import main.api.data.combined.AnsweredQuestion;
import main.api.data.combined.PollInfo;
import main.api.data.polls.PollResponse;
import main.api.data.questions.QuestionRequest;
import main.api.data.questions.QuestionResponse;
import main.api.data.questions.SimpleQuestionResponse;
import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
import main.api.utils.SecurityUtil;
import main.database.dao.AnswerRepository;
import main.database.dao.GroupRepository;
import main.database.dao.PollRepository;
import main.database.dao.QuestionRepository;
import main.database.dto.AnswerData;
import main.database.dto.PollData;
import main.database.dto.QuestionData;
import main.database.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/polls")
public class PollResource {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping(value = "/{pid}/question",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<QuestionResponse> question(@PathVariable("pid") int pollId) throws ApplicationException {
        PollData data = pollRepository.getItem(pollId);
        return data.getQuestions().stream().map(QuestionResponse::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    PollResponse get(@PathVariable("pid") int pid) throws ApplicationException {
        return new PollResponse(pollRepository.getItem(pid));
    }

    @RequestMapping(value = "/{pid}/question", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    QuestionResponse addQuestion(@PathVariable("pid") int pollId, @RequestBody QuestionRequest request) throws ApplicationException {
        PollData poll = pollRepository.getItem(pollId);
        if(poll == null)
            throw new ApplicationException(ExceptionCode.ITEM_NOT_FOUND, pollId);
        QuestionData question = new QuestionData(request);
        question.setPoll(poll);
        questionRepository.createItem(question);
        return new QuestionResponse(question);
    }

    @RequestMapping(value = "/user/{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<PollInfo> getPolls(@PathVariable("uid") int uid) throws ApplicationException {
        List<PollData> polls = pollRepository.getUserPolls(uid);

        List<PollInfo> userPolls = new LinkedList<>();

        List<SimpleQuestionResponse> pollQuestions = null;
        for(PollData poll: polls) {
            pollQuestions = poll.getQuestions()
                    .stream()
                    .map(SimpleQuestionResponse::new)
                    .collect(Collectors.toList());

            List<AnsweredQuestion> answeredQuestions = new LinkedList<>();

            for(SimpleQuestionResponse question: pollQuestions){
                Set<AnswerResponse> userAnswers = answerRepository
                        .getUserAnswers(uid, question.getId())
                        .stream()
                        .map(AnswerResponse::new)
                        .collect(Collectors.toSet());

                answeredQuestions.add(new AnsweredQuestion(question, userAnswers));
            }

            PollInfo pollInfo = new PollInfo(new PollResponse(poll));
            pollInfo.setAnsweredQuestion(answeredQuestions);
            userPolls.add(pollInfo);
        }

        return userPolls;
    }

    @RequestMapping(value = "/start/{pid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse startPoll(@PathVariable("pid") int pollId, HttpServletRequest request) throws ApplicationException {
        PollData poll = pollRepository.getItem(pollId);

        UserData loggedInUser = securityUtil.getLoggedInUser(request);
        if (!loggedInUser.equals(poll.getChairman())) {
            throw new ApplicationException(ExceptionCode.NOT_ALLOWED);
        }

        poll.setOpen(true);
        pollRepository.modifyItem(poll);

        return new SimpleResponse("Started poll.");
    }

    @RequestMapping(value = "/stop/{pid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse stopPoll(@PathVariable("pid") int pollId, HttpServletRequest request) throws ApplicationException {
        PollData poll = pollRepository.getItem(pollId);

        UserData loggedInUser = securityUtil.getLoggedInUser(request);
        if (!loggedInUser.equals(poll.getChairman())) {
            throw new ApplicationException(ExceptionCode.NOT_ALLOWED);
        }

        poll.setOpen(false);
        pollRepository.modifyItem(poll);

        return new SimpleResponse("Stopped poll.");
    }
}
