package com.strixrs.service;

import com.strixrs.controller.*;
import com.strixrs.javafxmodfiedcontrol.ResearchButton;
import com.strixrs.model.FourHouses;
import com.strixrs.model.Report;
import com.strixrs.model.ReportComponent;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SpecificReportPaneService extends AbstractService{

    private Report currentReport;

    public SpecificReportPaneService(AbsctractController controller) {
        super(controller);
    }

    public void updateComponentsVBox() {

        MainController mainController = (MainController) controller;

        mainController.getVbReportComponent().getChildren().clear();
        for(ReportComponent reportComponent: currentReport.getComponents()){

            Button button = new ResearchButton(reportComponent.getName());
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    try {
                        launchComponentScreen(reportComponent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            mainController.getVbReportComponent().getChildren().add(button);
        }
    }

    public void launchComponentScreen(ReportComponent reportComponent) throws IOException {

        Stage stage = new Stage();

        FXMLLoader fxmlLoader = null;

        String componentType = reportComponent.getComponentType();

        switch (componentType){
            case "FourHouses":
                fxmlLoader = StaticUtil.getFXML(componentType);
        }

        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);

        FourHousesController fourHousesController = fxmlLoader.getController();
        fourHousesController.setStage(stage);
        fourHousesController.setComponent((FourHouses) reportComponent);
        fourHousesController.initializeVBEvocations();
        stage.show();
    }


    public void setCurrentReport(Report currentReport) {
        this.currentReport = currentReport;
    }

    public void launchSpecificReportAddScreen() throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = StaticUtil.getFXML("AddSpecificReport");

        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);

        stage.setScene(scene);

        AddSpecificReportController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setSpecificReportPaneService(this);

        stage.showAndWait();
    }

    public Report getCurrentReport() {
        return currentReport;
    }
}
