package main.api.resources;

import main.Status;
import main.api.data.SimpleResponse;
import main.api.data.answers.AnswerVoteRequest;
import main.api.data.questions.QuestionRequest;
import main.api.data.questions.QuestionResponse;
import main.api.data.questions.QuestionResultResponse;
import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
import main.api.utils.SecurityUtil;
import main.database.dao.AnswerRepository;
import main.database.dao.GroupRepository;
import main.database.dao.PollRepository;
import main.database.dao.QuestionRepository;
import main.database.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionResource {

    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private PollRepository pollRepository;

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

        if (question.getStatus() == Status.CLOSED) {
            throw new ApplicationException(ExceptionCode.VOTE_IS_CLOSED, question.getId());
        }

        question.setStatus(Status.OPEN);
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

        if (question.getStatus() == Status.CLOSED) {
            throw new ApplicationException(ExceptionCode.VOTE_IS_CLOSED, question.getId());
        }

        question.setStatus(Status.CLOSED);
        questionRepository.modifyItem(question);

        return new SimpleResponse("Stopped question.");
    }

    @RequestMapping(value = "/{qid}/vote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse vote(@PathVariable("qid") int questionId, @RequestBody AnswerVoteRequest answerVote, HttpServletRequest request) throws ApplicationException {
        QuestionData question = questionRepository.getItem(questionId);
        UserData loggedInUser = securityUtil.getLoggedInUser(request);

        if (!questionRepository.havePermissonToVote(question, loggedInUser)) {
            throw new ApplicationException(ExceptionCode.NOT_ALLOWED);
        }

        if (questionRepository.haveVotedAlready(question, loggedInUser)) {
            throw new ApplicationException(ExceptionCode.VOTED_ALREADY);
        }

        if (question.getStatus() != Status.OPEN) {
            throw new ApplicationException(ExceptionCode.VOTE_NOT_UNDERWAY, question.getId());
        }


        AnswerData answerData = answerRepository.getAnswer(question, answerVote.getAnswer());
        if(answerData == null)
            throw new ApplicationException(ExceptionCode.ANSWER_NOT_EXISTING);
        answerData.addUserWhoAnswered(loggedInUser);
        answerRepository.modifyItem(answerData);
        return new SimpleResponse("Voted!");

    }



    @RequestMapping(value = "/{qid}/answer", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse addAnswer(@PathVariable("qid") int questionId,
                        @RequestBody AnswerVoteRequest answerVote,
                        HttpServletRequest request) throws ApplicationException {

        PollData poll = pollRepository.getPollByQuestionId(questionId);
        UserData user = securityUtil.getLoggedInUser(request);
        QuestionData question = questionRepository.getItem(questionId);
        if(!question.getOpen() && user.getId() != poll.getChairman().getId())
            throw new ApplicationException(ExceptionCode.ACCESS_DENIED,"add answer");
        AnswerData answerData = answerRepository.getOrGenerateAnswer(question, answerVote.getAnswer());
        answerRepository.modifyItem(answerData);
        return new SimpleResponse("Added answer!");
    }

    @RequestMapping(value = "/{qid}/result",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody QuestionResultResponse result(@PathVariable("qid") int questionId) throws ApplicationException {
        QuestionData question = questionRepository.getItem(questionId);
        QuestionResultResponse response = new QuestionResultResponse(question);
        List<AnswerData> answers = answerRepository.getQuestionAnswers(questionId);
        for(AnswerData ans : answers){
            BigInteger count = answerRepository.countAnswerAnswers(ans.getId());
            response.addResult(ans,count);
        }
        return response;
    }
}
