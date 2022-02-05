package com.strixrs.service;

import com.strixrs.controller.*;
import com.strixrs.data.DataReports;
import com.strixrs.javafxmodfiedcontrol.ResearchButton;
import com.strixrs.model.*;
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
import java.util.Optional;

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

    private void launchComponentScreen(ReportComponent reportComponent) throws IOException {

        Stage stage = new Stage();

        FXMLLoader fxmlLoader = null;

        String componentType = reportComponent.getComponentType();

        fxmlLoader = StaticUtil.getFXML(componentType);

        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);

        switch (componentType){
            case "FourHouses":
                FourHousesController fourHousesController = fxmlLoader.getController();
                fourHousesController.setStage(stage);
                fourHousesController.setComponent((FourHouses) reportComponent);
                fourHousesController.initializeVBEvocations();
                fourHousesController.setSpecificReportPaneService(this);
                break;
            case "WordCloud":
                WordCloudController wordCloudController = fxmlLoader.getController();
                wordCloudController.setStage(stage);
                wordCloudController.setComponent((WordCloud) reportComponent);
                wordCloudController.initializeVBEvocations();
                wordCloudController.setSpecificReportPaneService(this);
                break;
            case "BarChart":
                BarChartScreenController barChartScreenController = fxmlLoader.getController();
                barChartScreenController.setStage(stage);
                barChartScreenController.setComponent((BarChartComponent) reportComponent);
                barChartScreenController.initializeVBEvocations();
                barChartScreenController.setSpecificReportPaneService(this);
                break;
            case "ScatterChart":
                ScatterChartScreenController scatterChartScreenController = fxmlLoader.getController();
                scatterChartScreenController.setStage(stage);
                scatterChartScreenController.setComponent((ScatterChartComponent) reportComponent);
                scatterChartScreenController.initializeVBEvocations();
                scatterChartScreenController.setSpecificReportPaneService(this);
                break;
        }

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

    public void deleteCurrentReport() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão do relatório " + currentReport.getTitle());
        alert.setContentText("Têm certeza que deseja excluir o relatório atual? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DataReports.deleteReport(currentReport.getTitle());

            MainController mainController = (MainController) controller;
            mainController.getMainControllerService().update();
            mainController.getBpReportPane().toFront();
        }
    }

    public Report getCurrentReport() {
        return currentReport;
    }

    @Override
    public void update(){
        updateComponentsVBox();
    }
}
