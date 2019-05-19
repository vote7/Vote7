package main.api.data.polls;

import main.database.dto.PollData;

public class AbstractPoll {

    private String name;
    private String description;

    public AbstractPoll(){}

    public AbstractPoll(PollData data){
        this.name = data.getName();
        this.description = data.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
