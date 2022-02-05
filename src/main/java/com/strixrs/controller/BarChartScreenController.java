package com.strixrs.controller;

import com.strixrs.data.DataReports;
import com.strixrs.model.*;
import com.strixrs.service.SpecificReportPaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BarChartScreenController extends AbsctractController {

    @FXML private ImageView btnClose;
    @FXML private ImageView btnMaximize;
    @FXML private ImageView btnIconify;
    @FXML private VBox vbEvocations;
    @FXML private Button btnGenerateBarChart;
    @FXML private Button btnSaveReport;
    @FXML private Button btnRemove;
    @FXML private BarChart<?, ?> barChart;

    private double xOffSet;
    private double yOffSet;

    private List<RadioButton> selectedRadioButtons = new ArrayList<>();

    private BarChartComponent reportComponent;

    private SpecificReportPaneService specificReportPaneService;

    public void initializeVBEvocations() throws IOException {

        stage.show();

        List<Question> questions = reportComponent.getReport().getResearch().getQuestions();

        ArrayList<Question> wordCloudEvocations = reportComponent.getEvocations();

        for (Question question : questions) {

            HBox hBox = new HBox();
            RadioButton radioButton = new RadioButton(question.getTitle());
            radioButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                    if (isNowSelected) {
                        selectedRadioButtons.add(radioButton);
                    } else {
                        selectedRadioButtons.remove(radioButton);
                    }
                }
            });

            hBox.getChildren().add(radioButton);

            if (wordCloudEvocations != null)
                for (Question fourHousesEvocation : wordCloudEvocations) {
                    if (question.getTitle().equals(fourHousesEvocation.getTitle())) {
                        radioButton.setSelected(true);
                    }
                }

            vbEvocations.getChildren().add(hBox);
        }

        generateBarChart();
    }

    @FXML
    private void handleActionEvent(ActionEvent actionEvent) throws IOException {

        if (actionEvent.getSource() == btnGenerateBarChart) {
            generateBarChart();
        }
        if (actionEvent.getSource() == btnSaveReport) {
            saveReport();
        }
        if (actionEvent.getSource() == btnRemove) {
            remove();
        }
    }

    public void generateBarChart() throws IOException {

        if (selectedRadioButtons.isEmpty())
            return;

        barChart.getData().clear();
        reportComponent.getEvocations().clear();

        for (RadioButton radioButton : selectedRadioButtons) {

            Research research = reportComponent.getReport().getResearch();
            for (Question question : research.getQuestions()) {
                if (question.getTitle().equals(radioButton.getText())) {
                    reportComponent.getEvocations().add(question);
                }
            }
        }

        List<Answer> diffAnswers = new ArrayList<>();
        List<Integer> answersFrequences = new ArrayList<>();

        for(Question question: reportComponent.getEvocations()){
            for(Answer answer: question.getAnswers()){
                if(!containAnswer(diffAnswers, answer)){
                    int answerFrequence = calculateFrequence(answer.getAnswer());
                    diffAnswers.add(answer);
                    answersFrequences.add(answerFrequence);
                }
            }
        }


        for(int i = 0; i < diffAnswers.size(); i++){
            XYChart.Series series = new XYChart.Series<>();
            series.setName(diffAnswers.get(i).getAnswer());
            series.getData().add(new XYChart.Data(diffAnswers.get(i).getAnswer(), answersFrequences.get(i)));
            barChart.getData().addAll(series);
        }


    }

    private boolean containAnswer(List<Answer> answers, Answer answer) {

        for (Answer a : answers) {
            if (a.getAnswer().equals(answer.getAnswer()))
                return true;
        }

        return false;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        stageConfig();
    }

    private void stageConfig() {

        stage.initStyle(StageStyle.UNDECORATED);

        stage.getScene().setOnMousePressed((MouseEvent event) ->
        {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        stage.getScene().setOnMouseDragged((MouseEvent event) ->
        {
            if (!stage.isMaximized() || (stage.getX() != 0 || stage.getY() != 0)) {

                stage.setX(event.getScreenX() - xOffSet);
                stage.setY(event.getScreenY() - yOffSet);
            }
        });

        stage.setWidth(StaticUtil.screenWidth * 0.8);
        stage.setHeight(StaticUtil.screenHeight * 0.8);

        stage.centerOnScreen();

        stage.show();
    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(btnClose))
            stage.close();

        if (source.equals(btnIconify)) {
            stage.setIconified(true);
        }

        if (source.equals(btnMaximize)) {
            stage.setMaximized(!stage.isMaximized());
            if (stage.isMaximized()) {
                btnMaximize.setImage(StaticUtil.getIcon("white-minimize-hover.png"));
            } else {
                btnMaximize.setImage(StaticUtil.getIcon("white-maximize-hover.png"));
            }
        }
    }

    @FXML
    private void handleMouseEnteredEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(btnClose)) {
            btnClose.setImage(StaticUtil.getIcon("white-close-hover.png"));
        }

        if (source.equals(btnIconify)) {
            btnIconify.setImage(StaticUtil.getIcon("white-iconify-hover.png"));
        }

        if (source.equals(btnMaximize)) {
            if (stage.isMaximized()) {
                btnMaximize.setImage(StaticUtil.getIcon("white-minimize-hover.png"));
            } else {
                btnMaximize.setImage(StaticUtil.getIcon("white-maximize-hover.png"));
            }
        }
    }

    @FXML
    private void handleMouseExitedEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(btnClose)) {
            btnClose.setImage(StaticUtil.getIcon("white-close.png"));
        }

        if (source.equals(btnIconify)) {
            btnIconify.setImage(StaticUtil.getIcon("white-iconify.png"));
        }

        if (source.equals(btnMaximize)) {
            if (stage.isMaximized()) {
                btnMaximize.setImage(StaticUtil.getIcon("white-minimize.png"));
            } else {
                btnMaximize.setImage(StaticUtil.getIcon("white-maximize.png"));
            }
        }
    }

    public int calculateFrequence(String evocation) {

        int frequence = 0;

        for (Question question : reportComponent.getEvocations()) {
            for (Answer answer : question.getAnswers()) {
                if (answer.getAnswer().equals(evocation))
                    frequence += 1;
            }
        }

        return frequence;
    }


    private void saveReport() {
        DataReports.addReport(reportComponent.getReport());
    }

    public void setComponent(BarChartComponent reportComponent) {
        this.reportComponent = reportComponent;
    }

    private void remove() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão do Componente Atual");
        alert.setContentText("Têm certeza que deseja excluir o componente atual? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            reportComponent.getReport().getComponents().remove(reportComponent);
            saveReport();
            stage.close();
        }

        specificReportPaneService.updateComponentsVBox();
    }

    public void setSpecificReportPaneService(SpecificReportPaneService specificReportPaneService) {
        this.specificReportPaneService = specificReportPaneService;
    }
}
