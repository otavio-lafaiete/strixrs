package com.strixrs.model;

import java.util.ArrayList;

public class ScatterChartComponent implements ReportComponent {

    private String name;
    private final String componentType = "ScatterChart";
    private ArrayList<Question> evocations;
    private Report report;

    public ScatterChartComponent(String name) {
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
