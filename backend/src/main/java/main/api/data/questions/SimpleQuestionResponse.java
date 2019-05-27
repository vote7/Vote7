package main.api.data.questions;

import main.api.data.answers.AnswerResponse;
import main.database.dto.QuestionData;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleQuestionResponse extends AbstractQuestion {

    private Integer id;

    public SimpleQuestionResponse(QuestionData data){
        super(data);
        this.id = data.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
