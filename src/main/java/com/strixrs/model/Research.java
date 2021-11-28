package com.strixrs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Research implements Serializable {

    private String title;
    private String description;
    private List<Question> questions;

    public Research(String title, String description){

        this.title = title;
        this.description = description;
        questions = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
