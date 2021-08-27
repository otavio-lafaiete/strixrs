package com.strixrs.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private String title;
    private List<Answer> answers;

    public Question(String title){

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
