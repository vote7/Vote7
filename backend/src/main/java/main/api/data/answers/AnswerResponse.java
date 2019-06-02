package main.api.data.answers;

import main.database.dto.AnswerData;
import java.util.Date;

public class AnswerResponse {
    private int id;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public AnswerResponse(int id, String content, Date createdAt, Date updatedAt, int questionID) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public AnswerResponse(AnswerData data) {
        this.id = data.getId();
        this.content = data.getContent();
        this.createdAt = data.getCreatedAt();
        this.updatedAt = data.getUpdatedAt();
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

    public String toString(){
        return content;
    }
}
