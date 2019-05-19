package main.api.data.questions;

import main.database.dto.AnswerData;

public class AnswerResponse {
    private String content;

    public AnswerResponse(AnswerData data){
        this.content = data.getContent();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
