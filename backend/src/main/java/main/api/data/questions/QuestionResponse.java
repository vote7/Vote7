package main.api.data.questions;

import main.api.data.UserResponse;
import main.database.dto.QuestionData;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionResponse extends WSAbstractQuestion{

    private String image;
    private List<AnswerResponse> answers;

    public QuestionResponse(QuestionData data){
        super(data);
        this.image = data.getImage();
        this.answers = data.getAnswers().stream().map(AnswerResponse::new).collect(Collectors.toList());
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<AnswerResponse> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerResponse> answers) {
        this.answers = answers;
    }
}
