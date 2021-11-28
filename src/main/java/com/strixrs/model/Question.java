package com.strixrs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {

    private String title;
    private List<Answer> answers;
    private Research research;

    public Question(String title, Research research){
        answers = new ArrayList<>();
        this.title = title;
        this.research = research;
    }

    public String getTitle() {
        return title;
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

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }
}
