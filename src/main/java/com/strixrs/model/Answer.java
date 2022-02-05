package com.strixrs.model;

import java.io.Serializable;

public class Answer implements Serializable {

    private String answer;
    private Question question;

    public Answer(String answer, Question question){

        this.answer = answer;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
