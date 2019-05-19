package main.api.data.questions;

import main.database.dto.QuestionData;

import java.util.List;

public class AbstractQuestion {

    private int id;
    private String content;
    private Integer order;
    private boolean open;

    public AbstractQuestion(){}

    public AbstractQuestion(QuestionData data){
        this.id = data.getId();
        this.content = data.getContent();
        this.order = data.getOrder();
        this.open = data.isOpen();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
