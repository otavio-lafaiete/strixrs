package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddReportController;
import com.strixrs.data.DataReports;
import com.strixrs.model.Report;
import com.strixrs.model.Research;


public class AddReportService extends AbstractService{

    public AddReportService(AbsctractController controller) {
        super(controller);
    }

    public void addReport(String name, Research research) {

        Report report = new Report(name, research);

        DataReports.addReport(report);

        AddReportController addReportController = (AddReportController) controller;
        addReportController.getStage().close();
        addReportController.getReportPaneService().updateReportsVBox();
    }

}
