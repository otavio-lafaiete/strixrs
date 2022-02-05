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
import javafx.scene.chart.ScatterChart;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ScatterChartScreenController extends AbsctractController {

    @FXML private ImageView btnClose;
    @FXML private ImageView btnMaximize;
    @FXML private ImageView btnIconify;
    @FXML private VBox vbEvocations;
    @FXML private Button btnGenerateBarChart;
    @FXML private Button btnSaveReport;
    @FXML private Button btnRemove;
    @FXML private ScatterChart<String, Double> scatterChart;

    private double xOffSet;
    private double yOffSet;

    private List<RadioButton> selectedRadioButtons = new ArrayList<>();

    private ScatterChartComponent reportComponent;

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

        generateScatterChart();
    }

    @FXML
    private void handleActionEvent(ActionEvent actionEvent) throws IOException {

        if (actionEvent.getSource() == btnGenerateBarChart) {
            generateScatterChart();
        }
        if (actionEvent.getSource() == btnSaveReport) {
            saveReport();
        }
        if (actionEvent.getSource() == btnRemove) {
            remove();
        }
    }

    public void generateScatterChart() throws IOException {

        if (selectedRadioButtons.isEmpty())
            return;

        scatterChart.getData().clear();
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
        List<Float> answersFrequences = new ArrayList<>();
        List<Float> answersOrders = new ArrayList<>();

        for(Question question: reportComponent.getEvocations()){

            for(Answer answer: question.getAnswers()){
                if(!containAnswer(diffAnswers, answer)){
                    float answerFrequence = calculateFrequence(answer.getAnswer());
                    diffAnswers.add(answer);
                    answersFrequences.add(answerFrequence);
                    answersOrders.add(calculateOrder(answer.getAnswer()));
                }
            }
        }



        order(diffAnswers, answersFrequences, answersOrders);

        for(int i = 0; i < answersFrequences.size(); i++){
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(diffAnswers.get(i).getAnswer());
            series.getData().add(new XYChart.Data(answersFrequences.get(i).toString(), answersOrders.get(i)));
            scatterChart.getData().add(series);
        }

    }

    public float calculateOrder(String evocation){

        ArrayList<Question> questions = reportComponent.getEvocations();

        ArrayList<Integer> evocationPosition = new ArrayList<>();

        for(int i = 0; i < questions.size(); i++)
            evocationPosition.add(0);

        for(int i = 0; i < questions.size(); i++){

            for(Answer answer: questions.get(i).getAnswers()){

                if(answer.getAnswer().equals(evocation))
                    evocationPosition.set(i, evocationPosition.get(i) + 1);
            }
        }

        float total = 0;
        int sum = 0;

        for(int i = 1; i <= evocationPosition.size(); i++){

            total += evocationPosition.get(i - 1);
            sum += evocationPosition.get(i - 1) * i;
        }

        if(total == 0)
            return  0;

        return sum/total;
    }

    private boolean containAnswer(List<Answer> answers, Answer answer) {

        for (Answer a : answers) {
            if (a.getAnswer().equals(answer.getAnswer()))
                return true;
        }

        return false;
    }

    private void order(List<Answer> answers, List<Float> frequences, List<Float> orders){

        for(int x = 0; x < frequences.size() - 1; x++){
            for(int y = x + 1; y < frequences.size(); y++){

                if(frequences.get(x).compareTo(frequences.get(y)) > 0){

                    switchFloat(frequences, x, y);
                    switchFloat(orders, x, y);
                    switchAnswer(answers, x, y);
                }
            }
        }
    }

    private void switchFloat(List<Float> list, int x, int y){

        for(Float f: list){
            System.out.println(f);
        }

        System.out.println("------------------troca-----------------------------");
        System.out.println(x + " por " + y);
        Float v = list.get(x);
        list.set(x, list.get(y));
        list.set(y, v);


        for(Float f: list){
            System.out.println(f);
        }

        System.out.println("-----------------------------------------------");
    }

    private void switchAnswer(List<Answer> list, int x, int y){

        Answer v = list.get(x);
        list.set(x, list.get(y));
        list.set(y, v);
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

    public float calculateFrequence(String evocation) {

        int frequence = 0;
        int totalAnwers = 0;

        for(Question question: reportComponent.getEvocations()){
            for (Answer answer: question.getAnswers()){
                totalAnwers += 1;
                if(answer.getAnswer().equals(evocation))
                    frequence += 1;
            }
        }

        return (frequence * 100)/ (float) totalAnwers;
    }


    private void saveReport() {
        DataReports.addReport(reportComponent.getReport());
    }

    public void setComponent(ScatterChartComponent reportComponent) {
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
