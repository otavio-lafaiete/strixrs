package com.strixrs.controller;

import com.strixrs.data.DataReports;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.*;
import com.strixrs.service.SpecificReportPaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FourHousesController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnMaximize;
    @FXML private ImageView btnIconify;
    @FXML private VBox vbEvocations;
    @FXML private GridPane gpFirstSquare;
    @FXML private GridPane gpSecondSquare;
    @FXML private GridPane gpThirdSquare;
    @FXML private GridPane gpFourthSquare;
    @FXML private Button btnGenerateFourHouses;
    @FXML private Button btnAdjusteParameters;
    @FXML private Button btnSaveReport;
    @FXML private Button btnRemove;
    @FXML private TextField txtFrequence;
    @FXML private TextField txtOrder;
    @FXML private TextField txtMinimumFrequence;
    @FXML private Label lblFirstSquare;
    @FXML private Label lblSecondSquare;
    @FXML private Label lblThirdSquare;
    @FXML private Label lblFourthSquare;

    private static Font font = Font.font("System Regular", FontWeight.BOLD, 12);

    private double xOffSet;
    private double yOffSet;

    private List<RadioButton> selectedRadioButtons = new ArrayList<>();

    private FourHouses reportComponent;

    private SpecificReportPaneService specificReportPaneService;

    public void initializeVBEvocations(){

        List<Question> questions = reportComponent.getReport().getResearch().getQuestions();

        ArrayList<Question> fourHousesEvocations = reportComponent.getEvocations();

        for(Question question: questions){

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

            if(fourHousesEvocations != null)
            for(Question fourHousesEvocation: fourHousesEvocations){
                if(question.getTitle().equals(fourHousesEvocation.getTitle())){
                    radioButton.setSelected(true);
                }
            }

            vbEvocations.getChildren().add(hBox);
        }

        generateFourHouses();
        updateFourHousesLabels();
    }

    private void updateFourHousesLabels() {

        lblFirstSquare.setFont(font);
        lblSecondSquare.setFont(font);
        lblThirdSquare.setFont(font);
        lblFourthSquare.setFont(font);

        lblFirstSquare.setText("1º:\t Frequência >= "+ reportComponent.getFrequence() +
                " e Ordem < " + reportComponent.getEvocationOrder());
        lblSecondSquare.setText("2º:\t Frequência >= "+ reportComponent.getFrequence() +
                " e Ordem >= " + reportComponent.getEvocationOrder());
        lblThirdSquare.setText("3º:\t Frequência < "+ reportComponent.getFrequence() +
                " e Ordem < " + reportComponent.getEvocationOrder());
        lblFourthSquare.setText("4º:\t Frequência < "+ reportComponent.getFrequence() +
                " e Ordem >= " + reportComponent.getEvocationOrder());
    }

    @FXML
    private void handleActionEvent(ActionEvent actionEvent) throws IOException {

        if(actionEvent.getSource() == btnGenerateFourHouses){
            generateFourHouses();
        }
        if(actionEvent.getSource() == btnAdjusteParameters){
            String frequence = txtFrequence.getText();
            String minimumFrequence = txtMinimumFrequence.getText();
            String order = txtOrder.getText();
            adjusteParameters(frequence.isBlank()?0:Float.valueOf(frequence),
                    minimumFrequence.isBlank()?0:Float.valueOf(minimumFrequence),
                    order.isBlank()?0:Float.valueOf(order));
        }
        if(actionEvent.getSource() == btnSaveReport){
            saveReport();
        }
        if(actionEvent.getSource() == btnRemove){
            remove();
        }
    }

    public void generateFourHouses(){
        gpFirstSquare.getChildren().clear();
        gpSecondSquare.getChildren().clear();
        gpThirdSquare.getChildren().clear();
        gpFourthSquare.getChildren().clear();

        setHeader();

        reportComponent.getEvocations().clear();
        for(RadioButton radioButton: selectedRadioButtons){

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
                    if(answerFrequence < reportComponent.getMinimumFrequence())
                        continue;
                    diffAnswers.add(answer);
                    answersFrequences.add(answerFrequence);
                    answersOrders.add(calculateOrder(answer.getAnswer()));
                }
            }
        }

        if(reportComponent.getFrequence() == 0){

            float totalFrequences = 0;

            for (float frequence: answersFrequences){
                totalFrequences += frequence;
            }

            if(answersFrequences.size() > 0)
                reportComponent.setFrequence(totalFrequences/answersFrequences.size());
        }


        if(reportComponent.getEvocationOrder() == 0){

            float totalOrders = 0;

            for (float order: answersOrders){
                totalOrders += order;
            }

            if(answersOrders.size() > 0)
                reportComponent.setEvocationOrder(totalOrders/answersOrders.size());
        }

        txtFrequence.setText(String.valueOf(reportComponent.getFrequence()));
        txtMinimumFrequence.setText(String.valueOf(reportComponent.getMinimumFrequence()));
        txtOrder.setText(String.valueOf(reportComponent.getEvocationOrder()));

        List<Answer> diffAnswersFirstSquare = new ArrayList<>();
        List<Float> answersFrequencesFirstSquare = new ArrayList<>();
        List<Float> answersOrdersFirstSquare = new ArrayList<>();

        List<Answer> diffAnswersSecondSquare = new ArrayList<>();
        List<Float> answersFrequencesSecondSquare = new ArrayList<>();
        List<Float> answersOrdersSecondSquare = new ArrayList<>();

        List<Answer> diffAnswersThirdSquare = new ArrayList<>();
        List<Float> answersFrequencesThirdSquare = new ArrayList<>();
        List<Float> answersOrdersThirdSquare = new ArrayList<>();

        List<Answer> diffAnswersFourthSquare = new ArrayList<>();
        List<Float> answersFrequencesFourthSquare = new ArrayList<>();
        List<Float> answersOrdersFourthSquare = new ArrayList<>();

        //first square
        for(int i = 0; i < diffAnswers.size(); i++){

            if(answersFrequences.get(i) >= reportComponent.getFrequence() && answersOrders.get(i) < reportComponent.getEvocationOrder()){

                diffAnswersFirstSquare.add(diffAnswers.get(i));
                answersFrequencesFirstSquare.add(answersFrequences.get(i));
                answersOrdersFirstSquare.add(answersOrders.get(i));
            }
        }

        //Second square
        for(int i = 0; i < diffAnswers.size(); i++){

            if(answersFrequences.get(i) >= reportComponent.getFrequence() && answersOrders.get(i) >= reportComponent.getEvocationOrder()){

                diffAnswersSecondSquare.add(diffAnswers.get(i));
                answersFrequencesSecondSquare.add(answersFrequences.get(i));
                answersOrdersSecondSquare.add(answersOrders.get(i));
            }
        }

        //Third square
        for(int i = 0; i < diffAnswers.size(); i++){

            if(answersFrequences.get(i) < reportComponent.getFrequence() && answersOrders.get(i) < reportComponent.getEvocationOrder()){

                diffAnswersThirdSquare.add(diffAnswers.get(i));
                answersFrequencesThirdSquare.add(answersFrequences.get(i));
                answersOrdersThirdSquare.add(answersOrders.get(i));
            }
        }

        //Fourth square
        for(int i = 0; i < diffAnswers.size(); i++){

            if(answersFrequences.get(i) < reportComponent.getFrequence() && answersOrders.get(i) >= reportComponent.getEvocationOrder()){

                diffAnswersFourthSquare.add(diffAnswers.get(i));
                answersFrequencesFourthSquare.add(answersFrequences.get(i));
                answersOrdersFourthSquare.add(answersOrders.get(i));
            }
        }

        order(diffAnswersFirstSquare, answersFrequencesFirstSquare, answersOrdersFirstSquare);
        order(diffAnswersSecondSquare, answersFrequencesSecondSquare, answersOrdersSecondSquare);
        order(diffAnswersThirdSquare, answersFrequencesThirdSquare, answersOrdersThirdSquare);
        order(diffAnswersFourthSquare, answersFrequencesFourthSquare, answersOrdersFourthSquare);

        for(int i = 0; i < diffAnswersFirstSquare.size(); i++){
            gpFirstSquare.add(new Label(String.valueOf(answersFrequencesFirstSquare.get(i))), 0, i + 1);
            gpFirstSquare.add(new Label(diffAnswersFirstSquare.get(i).getAnswer()), 1, i + 1);
            gpFirstSquare.add(new Label(String.valueOf(answersOrdersFirstSquare.get(i))), 2, i + 1);
        }

        for(int i = 0; i < diffAnswersSecondSquare.size(); i++){
            gpSecondSquare.add(new Label(String.valueOf(answersFrequencesSecondSquare.get(i))), 0, i + 1);
            gpSecondSquare.add(new Label(diffAnswersSecondSquare.get(i).getAnswer()), 1, i + 1);
            gpSecondSquare.add(new Label(String.valueOf(answersOrdersSecondSquare.get(i))), 2, i + 1);
        }

        for(int i = 0; i < diffAnswersThirdSquare.size(); i++){
            gpThirdSquare.add(new Label(String.valueOf(answersFrequencesThirdSquare.get(i))), 0, i + 1);
            gpThirdSquare.add(new Label(diffAnswersThirdSquare.get(i).getAnswer()), 1, i + 1);
            gpThirdSquare.add(new Label(String.valueOf(answersOrdersThirdSquare.get(i))), 2, i + 1);
        }

        for(int i = 0; i < diffAnswersFourthSquare.size(); i++){
            gpFourthSquare.add(new Label(String.valueOf(answersFrequencesFourthSquare.get(i))), 0, i + 1);
            gpFourthSquare.add(new Label(diffAnswersFourthSquare.get(i).getAnswer()), 1, i + 1);
            gpFourthSquare.add(new Label(String.valueOf(answersOrdersFourthSquare.get(i))), 2, i + 1);
        }

        updateFourHousesLabels();
    }

    private void setHeader() {

        Label lblFrequence1 = new Label("Frequência");
        Label lblEvocation1 = new Label("Evocação");
        Label lblOrder1 = new Label("Ordem");

        Label lblFrequence2 = new Label("Frequência");
        Label lblEvocation2 = new Label("Evocação");
        Label lblOrder2 = new Label("Ordem");

        Label lblFrequence3 = new Label("Frequência");
        Label lblEvocation3 = new Label("Evocação");
        Label lblOrder3 = new Label("Ordem");

        Label lblFrequence4 = new Label("Frequência");
        Label lblEvocation4 = new Label("Evocação");
        Label lblOrder4 = new Label("Ordem");

        lblFrequence1.setFont(font);
        lblEvocation1.setFont(font);
        lblOrder1.setFont(font);

        lblFrequence2.setFont(font);
        lblEvocation2.setFont(font);
        lblOrder2.setFont(font);

        lblFrequence3.setFont(font);
        lblEvocation3.setFont(font);
        lblOrder3.setFont(font);

        lblFrequence4.setFont(font);
        lblEvocation4.setFont(font);
        lblOrder4.setFont(font);

        gpFirstSquare.add(lblFrequence1, 0, 0);
        gpFirstSquare.add(lblEvocation1, 1, 0);
        gpFirstSquare.add(lblOrder1, 2, 0);

        gpSecondSquare.add(lblFrequence2, 0, 0);
        gpSecondSquare.add(lblEvocation2, 1, 0);
        gpSecondSquare.add(lblOrder2, 2, 0);

        gpThirdSquare.add(lblFrequence3, 0, 0);
        gpThirdSquare.add(lblEvocation3, 1, 0);
        gpThirdSquare.add(lblOrder3, 2, 0);

        gpFourthSquare.add(lblFrequence4, 0, 0);
        gpFourthSquare.add(lblEvocation4, 1, 0);
        gpFourthSquare.add(lblOrder4, 2, 0);
    }

    private boolean containAnswer(List<Answer> answers, Answer answer){

        for(Answer a: answers){
            if(a.getAnswer().equals(answer.getAnswer()))
                return true;
        }

        return false;
    }

    private void order(List<Answer> answers, List<Float> frequences, List<Float> orders){

        for(int x = 0; x < frequences.size() - 1; x++){

            for(int y = 1; y < frequences.size(); y++){

                if(frequences.get(x) < frequences.get(y)){

                    switchFloat(frequences, x, y);
                    switchFloat(orders, x, y);
                    switchAnswer(answers, x, y);
                }
            }
        }
    }

    private void switchFloat(List<Float> list, int x, int y){

        Float v = list.get(x);
        list.set(x, list.get(y));
        list.set(y, v);
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

    private void stageConfig(){

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
    private void handleMouseEnteredEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose)){
            btnClose.setImage(StaticUtil.getIcon("white-close-hover.png"));
        }

        if(source.equals(btnIconify)){
            btnIconify.setImage(StaticUtil.getIcon("white-iconify-hover.png"));
        }

        if(source.equals(btnMaximize)){
            if(stage.isMaximized()){
                btnMaximize.setImage(StaticUtil.getIcon("white-minimize-hover.png"));
            }else{
                btnMaximize.setImage(StaticUtil.getIcon("white-maximize-hover.png"));
            }
        }
    }

    @FXML
    private void handleMouseExitedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose)){
            btnClose.setImage(StaticUtil.getIcon("white-close.png"));
        }

        if(source.equals(btnIconify)){
            btnIconify.setImage(StaticUtil.getIcon("white-iconify.png"));
        }

        if(source.equals(btnMaximize)) {
            if(stage.isMaximized()){
                btnMaximize.setImage(StaticUtil.getIcon("white-minimize.png"));
            }else{
                btnMaximize.setImage(StaticUtil.getIcon("white-maximize.png"));
            }
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

    public float calculateFrequence(String evocation){

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

    private void adjusteParameters(float frequence, float minimumfrequence, float evocationOrder){

        reportComponent.setFrequence(frequence);
        reportComponent.setMinimumFrequence(minimumfrequence);
        reportComponent.setEvocationOrder(evocationOrder);
        updateFourHousesLabels();
        generateFourHouses();
    }

    private void saveReport(){

        DataReports.addReport(reportComponent.getReport());
    }

    public void setComponent(FourHouses reportComponent) {
        this.reportComponent = reportComponent;
    }

    private void remove(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão do Componente Atual");
        alert.setContentText("Têm certeza que deseja excluir o componente atual? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
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
