package main.api.resources;

import main.api.data.questions.QuestionResponse;
import main.api.utils.ApplicationException;
import main.database.dao.PollRepository;
import main.database.dao.QuestionRepository;
import main.database.dto.PollData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/polls")
public class PollResource {

    @Autowired
    private PollRepository pollRepository;

    @RequestMapping(value = "/{pid}/question",method = RequestMethod.GET)
    public @ResponseBody
    List<QuestionResponse> question(@PathVariable("pid") int pollId) throws ApplicationException {
        //TODO sprawdzic czy dziala
        PollData data = pollRepository.getItem(pollId);
        return data.getQuestions().stream().map(QuestionResponse::new).collect(Collectors.toList());
    }
}
