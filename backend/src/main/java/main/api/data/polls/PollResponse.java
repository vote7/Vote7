package main.api.data.polls;

import main.database.dto.PollData;

import java.util.Date;

public class PollResponse extends AbstractPoll {

    private int id;
    private Date date;

    public PollResponse(PollData data){
        super(data);
        this.id = data.getId();
        this.date = data.getDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
