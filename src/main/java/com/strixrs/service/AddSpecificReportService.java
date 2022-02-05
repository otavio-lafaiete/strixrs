package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddSpecificReportController;
import com.strixrs.data.DataReports;
import com.strixrs.model.*;

public class AddSpecificReportService extends AbstractService{

    public AddSpecificReportService(AbsctractController controller) {
        super(controller);
    }

    public void addComponent(String name, String tipo) {

        AddSpecificReportController addSpecificReportController = (AddSpecificReportController) controller;

        Report currentReport = addSpecificReportController.getSpecificReportPaneService().getCurrentReport();

        switch (tipo){
            case "FourHouses":
                FourHouses fourHouses = new FourHouses(name);
                fourHouses.setReport(currentReport);
                currentReport.getComponents().add(fourHouses);
                break;
            case "WordCloud":
                WordCloud wordCloud = new WordCloud(name);
                wordCloud.setReport(currentReport);
                currentReport.getComponents().add(wordCloud);
                break;
            case "BarChart":
                BarChartComponent barChartComponent = new BarChartComponent(name);
                barChartComponent.setReport(currentReport);
                currentReport.getComponents().add(barChartComponent);
                break;
            case "ScatterChart":
                ScatterChartComponent scatterChartComponent = new ScatterChartComponent(name);
                scatterChartComponent.setReport(currentReport);
                currentReport.getComponents().add(scatterChartComponent);
                break;
        }

        DataReports.addReport(currentReport);

        addSpecificReportController.getSpecificReportPaneService().updateComponentsVBox();
        addSpecificReportController.getStage().close();
    }
}
