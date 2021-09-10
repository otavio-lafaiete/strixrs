package com.strixrs.model;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Answer implements Serializable {

    private String answer;
    private String id;
    private Question question;

    public Answer(String answer, String id, Question question){

        this.answer = answer;
        this.id = id;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
