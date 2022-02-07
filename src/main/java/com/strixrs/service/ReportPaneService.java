package com.strixrs.service;

import com.strixrs.controller.*;
import com.strixrs.data.DataReports;
import com.strixrs.data.DataResearchs;
import com.strixrs.javafxmodfiedcontrol.ResearchButton;
import com.strixrs.model.Report;
import com.strixrs.model.Research;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ReportPaneService extends AbstractService {

    public ReportPaneService(AbsctractController controller) {
        super(controller);
    }

    public void updateReportsVBox() {

        MainController mainController = (MainController) controller;

        List<Report> listReports = DataReports.getReports();
        List<Research> listResearch = DataResearchs.getResearchs();

        for (int i = 0; i < listReports.size(); i++) {
            for (int y = 0; y < listResearch.size(); y++) {
                if (listReports.get(i).getResearch().getTitle().equals(listResearch.get(y).getTitle())) {
                    listReports.get(i).setResearch(listResearch.get(y));
                    DataReports.addReport(listReports.get(i));
                    break;
                }
            }
        }

        mainController.getVbReports().getChildren().clear();
        for (Report report : DataReports.getReports()) {

            Button button = new ResearchButton(report.getTitle());
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    mainController.getSpecificReportPaneService().setCurrentReport(report);
                    mainController.getBpSpecificReportPane().toFront();
                    mainController.getSpecificReportPaneService().updateComponentsVBox();
                    mainController.getLblSpecificReport().setText(report.getTitle());
                }
            });
            mainController.getVbReports().getChildren().add(button);
        }
    }

    public void launchReportAddScreen() throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = StaticUtil.getFXML("AddReport");

        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);

        stage.setScene(scene);

        AddReportController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setReportPaneService(this);

        stage.showAndWait();
    }

    @Override
    public void update() {
        updateReportsVBox();
    }
}
