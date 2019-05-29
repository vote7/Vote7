package main.api.data.questions;

import main.database.dto.QuestionData;

public class AbstractQuestion {

    private String content;
    private Boolean open;
    private String image;

    public AbstractQuestion(){}

    public AbstractQuestion(QuestionData data){
        this.content = data.getContent();
        this.open = data.getOpen();
        this.image = data.getImage();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
