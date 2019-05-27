package main.api.data.combined;

import main.api.data.polls.PollResponse;

import java.util.List;

public class PollInfo {
    private PollResponse poll;
    private List<AnsweredQuestion> answeredQuestion;

    public PollInfo(PollResponse poll, List<AnsweredQuestion> answeredQuestion) {
        this.poll = poll;
        this.answeredQuestion = answeredQuestion;
    }
}
