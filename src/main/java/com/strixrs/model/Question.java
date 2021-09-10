package com.strixrs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {

    private String title;
    private List<Answer> answers;
    private String answerType;
    private Research research;

    public Question(String title, String answerType, Research research){
        answers = new ArrayList<>();
        this.title = title;
        this.answerType = answerType;
        this.research = research;
    }

    public String getTitle() {
        return title;
    }

    public String getAnswerType(){
        return answerType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }
}
