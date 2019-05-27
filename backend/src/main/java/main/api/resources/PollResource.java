package main.api.resources;

import main.api.data.groups.GroupResponse;
import main.api.data.polls.PollResponse;
import main.api.data.questions.QuestionRequest;
import main.api.data.questions.QuestionResponse;
import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
import main.database.dao.PollRepository;
import main.database.dao.QuestionRepository;
import main.database.dto.AnswerData;
import main.database.dto.PollData;
import main.database.dto.QuestionData;
import org.apache.tomcat.jni.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/polls")
public class PollResource {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private QuestionRepository questionRepository;

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
}
