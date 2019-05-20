package main.api.resources;

import main.api.data.groups.GroupResponse;
import main.api.data.polls.PollResponse;
import main.api.data.questions.QuestionResponse;
import main.api.utils.ApplicationException;
import main.database.dao.PollRepository;
import main.database.dao.QuestionRepository;
import main.database.dto.AnswerData;
import main.database.dto.PollData;
import main.database.dto.QuestionData;
import org.apache.tomcat.jni.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/polls")
public class PollResource {

    @Autowired
    private PollRepository pollRepository;

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
}
