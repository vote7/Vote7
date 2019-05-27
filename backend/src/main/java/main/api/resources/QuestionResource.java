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
@RequestMapping("/questions")
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

    @RequestMapping(value = "{qid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "changeOrder/{qid1}/{qid2}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SimpleResponse changeOrder(@PathVariable("qid1") int qid1, @PathVariable("qid2") int qid2) throws ApplicationException {
        QuestionData firstQuestion = questionRepository.getItem(qid1);
        if(firstQuestion == null)
            throw new ApplicationException(ExceptionCode.ITEM_NOT_FOUND, qid1);

        QuestionData secondQuestion = questionRepository.getItem(qid1);
        if(secondQuestion == null)
            throw new ApplicationException(ExceptionCode.ITEM_NOT_FOUND, qid2);

        firstQuestion.switchOrders(secondQuestion);

        questionRepository.modifyItem(firstQuestion);
        questionRepository.modifyItem(secondQuestion);

        String response = String.format("Question %d and %d successfully changedOrder", qid1, qid2);
        return new SimpleResponse(response);
    }
}
