package main.api.resources;

import main.api.data.SimpleResponse;
import main.api.data.questions.QuestionRequest;
import main.api.data.questions.QuestionResponse;
import main.api.utils.ApplicationException;
import main.api.utils.ExceptionCode;
import main.database.dao.QuestionRepository;
import main.database.dto.QuestionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/question")
public class QuestionResource {

    @Autowired
    private QuestionRepository questionRepository;

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
}
