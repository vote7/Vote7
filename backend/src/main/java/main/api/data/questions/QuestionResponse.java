package main.api.data.questions;

import main.database.dto.QuestionData;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionResponse extends AbstractQuestion {

    private Integer id;
    private List<AnswerResponse> answers;
    private Integer order;

    public QuestionResponse(QuestionData data){
        super(data);
        this.id = data.getId();
        this.answers = data.getAnswers().stream().map(AnswerResponse::new).collect(Collectors.toList());
        this.order = data.getOrders();
    }

    public List<AnswerResponse> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerResponse> answers) {
        this.answers = answers;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
