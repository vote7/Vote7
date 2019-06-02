package main.api.data.combined;

import main.api.data.polls.PollResponse;

import java.util.List;

public class PollInfoResponse {
    private PollResponse poll;
    private List<AnsweredQuestionResponse> answeredQuestion;

    public PollInfoResponse(PollResponse poll) {
        this.poll = poll;
    }

    public List<AnsweredQuestionResponse> getAnsweredQuestion() {
        return answeredQuestion;
    }

    public void setAnsweredQuestion(List<AnsweredQuestionResponse> answeredQuestion) {
        this.answeredQuestion = answeredQuestion;
    }

    public PollResponse getPoll() {
        return poll;
    }

    public void setPoll(PollResponse poll) {
        this.poll = poll;
    }
}
