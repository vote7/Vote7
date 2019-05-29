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

    @RequestMapping(value = "putAfter/{qidSource}/{order}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse putOn(@PathVariable("qidSource") int qidSource, @PathVariable("order") int order) throws ApplicationException {
        QuestionData sourceQuestion = questionRepository.getItem(qidSource);
        if(sourceQuestion == null)
            throw new ApplicationException(ExceptionCode.ITEM_NOT_FOUND, qidSource);

        questionRepository.putOn(sourceQuestion, order);

        String response = String.format("Question %d successfully changed order to %d", qidSource, order);
        return new SimpleResponse(response);
    }

    @RequestMapping(value = "/start/{qid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse startQuestion(@PathVariable("qid") int questionId, HttpServletRequest request) throws ApplicationException {
        QuestionData question = questionRepository.getItem(questionId);
        PollData poll = question.getPoll();
        if (!poll.getOpen()) {
            throw new ApplicationException(ExceptionCode.POLL_IS_CLOSED, poll.getId());
        }

        UserData loggedInUser = securityUtil.getLoggedInUser(request);
        if (!loggedInUser.equals(poll.getChairman())) {
            throw new ApplicationException(ExceptionCode.NOT_ALLOWED);
        }

        question.setOpen(true);
        questionRepository.modifyItem(question);

        return new SimpleResponse("Started question.");
    }

    @RequestMapping(value = "/stop/{pid}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse stopQuestion(@PathVariable("qid") int questionId, HttpServletRequest request) throws ApplicationException {
        QuestionData question = questionRepository.getItem(questionId);
        PollData poll = question.getPoll();
        if (!poll.getOpen()) {
            throw new ApplicationException(ExceptionCode.POLL_IS_CLOSED, poll.getId());
        }

        UserData loggedInUser = securityUtil.getLoggedInUser(request);
        if (!loggedInUser.equals(poll.getChairman())) {
            throw new ApplicationException(ExceptionCode.NOT_ALLOWED);
        }

        question.setOpen(false);
        questionRepository.modifyItem(question);

        return new SimpleResponse("Stopped question.");
    }

    @RequestMapping(value = "/{qid}/vote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse vote(@PathVariable("qid") int questionId, @RequestBody AnswerVote answerVote, HttpServletRequest request) throws ApplicationException {
        QuestionData question = questionRepository.getItem(questionId);
        UserData loggedInUser = securityUtil.getLoggedInUser(request);

        if (questionRepository.canVote(question, loggedInUser)) {
            AnswerData answerData = answerRepository.getOrGenerateAnswer(question, answerVote.getAnswer());
            answerData.addUserWhoAnswered(loggedInUser);
            answerRepository.modifyItem(answerData);
            return new SimpleResponse("Voted!");
        } else {
            throw new ApplicationException(ExceptionCode.NOT_ALLOWED);
        }
    }

}
