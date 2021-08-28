package com.strixrs.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private String title;
    private List<Answer> answers;
    private String answerType;

    public Question(String title, String answerType){

        this.title = title;
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

}
