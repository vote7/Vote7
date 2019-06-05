package main.api.data.polls;

import main.database.dto.PollData;

import java.util.Date;

public class PollResponse extends AbstractPoll {

    private int id;
    private Date date;
    private int chairmanID;
    private int secretaryID;
    private boolean underway;

    public PollResponse(PollData data){
        super(data);
        this.id = data.getId();
        this.date = data.getDate();
        this.chairmanID = data.getChairman().getId();
        this.secretaryID = data.getSecretary().getId();
        this.underway = data.getUnderway() != null && data.getUnderway();
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

    public int getChairmanID() {
        return chairmanID;
    }

    public int getSecretaryID() {
        return secretaryID;
    }

    public void setSecretaryID(int secretaryID) {
        this.secretaryID = secretaryID;
    }

    public boolean getUnderway() {
        return underway;
    }
}
