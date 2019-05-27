package main.api.data.answers;

import java.util.Date;

public class AnswerResponse {
    private int id;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private int questionID;

    public AnswerResponse(int id, String content, Date createdAt, Date updatedAt, int questionID) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.questionID = questionID;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public int getQuestionID() {
        return questionID;
    }
}
