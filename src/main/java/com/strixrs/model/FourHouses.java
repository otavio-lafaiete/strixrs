package com.strixrs.model;

import java.util.ArrayList;

public class FourHouses implements ReportComponent {

    private String name;
    private final String componentType = "FourHouses";
    private float frequence;
    private float minimumFrequence;
    private float evocationOrder;
    private ArrayList<Question> evocations;
    private Report report;

    public FourHouses(String name) {
        this.name = name;
        evocations = new ArrayList<>();
    }

    @Override
    public String getComponentType() {
        return componentType;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getFrequence() {
        return frequence;
    }

    public void setFrequence(float frequence) {
        this.frequence = frequence;
    }

    public float getMinimumFrequence() {
        return minimumFrequence;
    }

    public void setMinimumFrequence(float minimumFrequence) {
        this.minimumFrequence = minimumFrequence;
    }

    public float getEvocationOrder() {
        return evocationOrder;
    }

    public void setEvocationOrder(float evocationOrder) {
        this.evocationOrder = evocationOrder;
    }

    @Override
    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public ArrayList<Question> getEvocations() {
        return evocations;
    }
}
