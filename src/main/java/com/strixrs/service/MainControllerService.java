package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.MainController;

public class MainControllerService extends AbstractService {

    public MainControllerService(AbsctractController controller) {
        super(controller);
    }

    public void update() {

        MainController mainController = (MainController) controller;

        mainController.getResearchPaneService().update();
        mainController.getExportPaneService().update();
        mainController.getReportPaneService().update();
        mainController.getQuestionPaneService().update();
    }
}
