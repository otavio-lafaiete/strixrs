package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddSpecificReportController;
import com.strixrs.data.DataReports;
import com.strixrs.model.FourHouses;
import com.strixrs.model.Report;

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
        }

        DataReports.addReport(currentReport);

        addSpecificReportController.getStage().close();
        addSpecificReportController.getSpecificReportPaneService().updateComponentsVBox();
    }
}
