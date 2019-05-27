package main.api.data.combined;

import main.api.data.polls.PollResponse;

import java.util.List;

public class PollInfo {
    private PollResponse poll;
    private List<AnsweredQuestion> answeredQuestion;

    public PollInfo(PollResponse poll) {
        this.poll = poll;
    }

    public List<AnsweredQuestion> getAnsweredQuestion() {
        return answeredQuestion;
    }

    public void setAnsweredQuestion(List<AnsweredQuestion> answeredQuestion) {
        this.answeredQuestion = answeredQuestion;
    }

    public PollResponse getPoll() {
        return poll;
    }

    public void setPoll(PollResponse poll) {
        this.poll = poll;
    }
}
