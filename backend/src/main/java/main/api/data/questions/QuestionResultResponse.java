package main.api.data.questions;

import main.api.data.answers.AnswerResponse;
import main.database.dto.AnswerData;
import main.database.dto.QuestionData;

import java.math.BigInteger;
import java.util.HashMap;

public class QuestionResultResponse {

    private QuestionResponse question;
    private HashMap<AnswerResponse,BigInteger> result;

    public QuestionResultResponse(QuestionData data){
        this.question = new QuestionResponse(data);
        this.result = new HashMap<>();
    }

    public void addResult(AnswerData data, BigInteger count){
        result.put(new AnswerResponse(data),count);
    }

    public QuestionResponse getQuestion() {
        return question;
    }

    public void setQuestion(QuestionResponse question) {
        this.question = question;
    }

    public HashMap<AnswerResponse, BigInteger> getResult() {
        return result;
    }

    public void setResult(HashMap<AnswerResponse, BigInteger> result) {
        this.result = result;
    }
}
