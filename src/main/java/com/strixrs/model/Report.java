package com.strixrs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Report implements Serializable {

    private String title;
    private Research research;
    private List<ReportComponent> components;

    public Report(String title, Research research){

        this.title = title;
        this.research = research;
        components = new ArrayList<>();
    }

    public List<ReportComponent> getComponents() {
        return components;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }
}
