package main.api.data.polls;

import java.util.Date;

public class PollRequest extends AbstractPoll{

    private int secretaryId;
    private int chairmanId;

    public PollRequest(){}

    public int getChairmanId() {
        return chairmanId;
    }

    public void setChairmanId(int chairmanId) {
        this.chairmanId = chairmanId;
    }

    public int getSecretaryId() {
        return secretaryId;
    }

    public void setSecretaryId(int secretaryId) {
        this.secretaryId = secretaryId;
    }
}
