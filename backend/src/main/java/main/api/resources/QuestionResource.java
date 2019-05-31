package main.api.resources;

import main.api.data.SimpleResponse;
import main.api.data.answers.AnswerVote;
import main.api.data.questions.QuestionRequest;
import main.api.data.questions.QuestionResponse;
import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
import main.api.utils.SecurityUtil;
import main.database.dao.AnswerRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/questions")
public class QuestionResource {
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping(value = "{qid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    QuestionResponse get(@PathVariable("qid") int id) throws ApplicationException {
        return new QuestionResponse(questionRepository.getItem(id));
    }

    @RequestMapping(value = "{qid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse delete(@PathVariable("qid") int id) throws ApplicationException {
        questionRepository.removeItem(id);
        String response = String.format("Question %d successfully deleted", id);
        return new SimpleResponse(response);
    }

    @RequestMapping(value = "{qid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse edit(@PathVariable("qid") int id, @RequestBody QuestionRequest request) throws ApplicationException {
        QuestionData data = questionRepository.getItem(id);
        if(data == null)
            throw new ApplicationException(ExceptionCode.ITEM_NOT_FOUND, id);

        data.handle(request);
        questionRepository.modifyItem(data);

        String response = String.format("Question %d successfully edited", id);
        return new SimpleResponse(response);
    }

    @RequestMapping(value = "putOn/{qid}/{order}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse putOn(@PathVariable("qid") int qid, @PathVariable("order") int order) throws ApplicationException {
        QuestionData question = questionRepository.getItem(qid);
        if(question == null)
            throw new ApplicationException(ExceptionCode.ITEM_NOT_FOUND, qid);

        questionRepository.putOn(question, order);

        String response = String.format("Question %d successfully changed order to %d", qid, order);
        return new SimpleResponse(response);
    }

    @RequestMapping(value = "/start/{qid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse startQuestion(@PathVariable("qid") int questionId, HttpServletRequest request) throws ApplicationException {
        QuestionData question = questionRepository.getItem(questionId);
        PollData poll = question.getPoll();
        if (!poll.getUnderway()) {
            throw new ApplicationException(ExceptionCode.POLL_IS_CLOSED, poll.getId());
        }

        UserData loggedInUser = securityUtil.getLoggedInUser(request);
        if (!loggedInUser.equals(poll.getChairman())) {
            throw new ApplicationException(ExceptionCode.NOT_ALLOWED);
        }

        question.setUnderway(true);
        questionRepository.modifyItem(question);

        return new SimpleResponse("Started question.");
    }

    @RequestMapping(value = "/stop/{qid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse stopQuestion(@PathVariable("qid") int questionId, HttpServletRequest request) throws ApplicationException {
        QuestionData question = questionRepository.getItem(questionId);
        PollData poll = question.getPoll();
        if (!poll.getUnderway()) {
            throw new ApplicationException(ExceptionCode.POLL_IS_CLOSED, poll.getId());
        }

        UserData loggedInUser = securityUtil.getLoggedInUser(request);
        if (!loggedInUser.equals(poll.getChairman())) {
            throw new ApplicationException(ExceptionCode.NOT_ALLOWED);
        }

        question.setUnderway(false);
        questionRepository.modifyItem(question);

        return new SimpleResponse("Stopped question.");
    }

    @RequestMapping(value = "/{qid}/vote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse vote(@PathVariable("qid") int questionId, @RequestBody AnswerVote answerVote, HttpServletRequest request) throws ApplicationException {
        QuestionData question = questionRepository.getItem(questionId);
        UserData loggedInUser = securityUtil.getLoggedInUser(request);

        if (!questionRepository.havePermissonToVote(question, loggedInUser)) {
            throw new ApplicationException(ExceptionCode.NOT_ALLOWED);
        }

        if (!question.getUnderway()) {
            throw new ApplicationException(ExceptionCode.VOTE_IS_CLOSED, question.getId());
        }

        if (questionRepository.haveVotedAlready(question, loggedInUser)) {
            throw new ApplicationException(ExceptionCode.VOTED_ALREADY);
        }


        AnswerData answerData = answerRepository.getOrGenerateAnswer(question, answerVote.getAnswer());
        answerData.addUserWhoAnswered(loggedInUser);
        answerRepository.modifyItem(answerData);
        return new SimpleResponse("Voted!");

    }



    @RequestMapping(value = "/{qid}/answer", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse vote(@PathVariable("qid") int questionId, @RequestBody AnswerVote answerVote) throws ApplicationException {

        //@TODO check if user can add answer
        QuestionData question = questionRepository.getItem(questionId);
        AnswerData answerData = answerRepository.getOrGenerateAnswer(question, answerVote.getAnswer());
        answerRepository.modifyItem(answerData);
        return new SimpleResponse("Added answer!");


    }

}
