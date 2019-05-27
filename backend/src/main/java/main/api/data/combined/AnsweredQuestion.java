package main.api.data.combined;


import main.api.data.questions.AnswerResponse;
import main.api.data.questions.QuestionResponse;

public class AnsweredQuestion {
    private QuestionResponse question;
    private AnswerResponse answer;

    public AnsweredQuestion(QuestionResponse question, AnswerResponse answer) {
        this.question = question;
        this.answer = answer;
    }
}
