package main.api.data.combined;


import main.api.data.answers.AnswerResponse;
import main.api.data.questions.SimpleQuestionResponse;

import java.util.Set;

public class AnsweredQuestion {
    private SimpleQuestionResponse question;
    private Set<AnswerResponse> answers;

    public AnsweredQuestion(SimpleQuestionResponse question, Set<AnswerResponse> answers) {
        this.question = question;
        this.answers = answers;
    }

    public SimpleQuestionResponse getQuestion() {
        return question;
    }

    public void setQuestion(SimpleQuestionResponse question) {
        this.question = question;
    }

    public Set<AnswerResponse> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerResponse> answers) {
        this.answers = answers;
    }
}
