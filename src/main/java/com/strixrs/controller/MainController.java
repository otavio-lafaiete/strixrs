package com.strixrs.controller;

import com.strixrs.model.Answer;
import com.strixrs.service.*;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnMaximize;
    @FXML private ImageView btnIconify;
    @FXML private AnchorPane anchorPane;
    @FXML private Button btnResearch;
    @FXML private Button btnImport;
    @FXML private Button btnExport;
    @FXML private BorderPane bpResearchPane;
    @FXML private BorderPane bpQuestionPane;
    @FXML private BorderPane bpAnswerPane;
    @FXML private BorderPane bpImportPane;
    @FXML private BorderPane bpExportPane;
    @FXML private TextField txtResearchSearch;
    @FXML private Button btnResearchSearch;
    @FXML private Button btnResearchAdd;
    @FXML private Button btnResearchUpdate;
    @FXML private Button btnQuestionAdd;
    @FXML private Button btnQuestionUpdate;
    @FXML private Button btnBackToResearchs;
    @FXML private Button btnAnswerAdd;
    @FXML private VBox vbResearchs;
    @FXML private VBox vbQuestions;
    @FXML private Label lblResearchTitle;
    @FXML private TextArea taResearchDescription;
    @FXML private Label lblQuestionTitle;
    @FXML private Button btnBackToQuestions;
    @FXML private TableView<Answer> tvAnswers;
    @FXML private TableColumn<Answer, String> tcAnswer;
    @FXML private TableColumn<Answer, String> tcID;
    @FXML private Button btnImportSelect;
    @FXML private TextField txtFilePath;
    @FXML private Button btnDoImport;
    @FXML private TextField txtExportPath;
    @FXML private Button btnExportSelect;
    @FXML private Button btnDoExport;
    @FXML private ListView<String> lvExportResearchs;

    ResearchPaneService researchPaneService;
    QuestionPaneService questionPaneService;
    AnswerPaneService answerPaneService;
    ImportPaneService importPaneService;
    ExportPaneService exportPaneService;

    public void initialize(){

        researchPaneService = new ResearchPaneService(this);
        researchPaneService.updateResearchsVBox();

        questionPaneService = new QuestionPaneService(this);

        answerPaneService = new AnswerPaneService(this);

        importPaneService = new ImportPaneService(this);

        exportPaneService = new ExportPaneService(this);
        exportPaneService.updateLVResearchs();

        tcAnswer.setCellValueFactory(new PropertyValueFactory<>("answer"));
        tcID.setCellValueFactory(new PropertyValueFactory<>("id"));

        tvAnswers.setItems(answerPaneService.getAnswersData());
    }

    //Main Scene Event Handlers
    @FXML
    private void handleMouseClickedEventFromMainScene(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose))
            System.exit(0);

        if(source.equals(btnIconify))
            stage.setIconified(true);

        if(source.equals(btnMaximize)){
            stage.setMaximized(!stage.isMaximized());
            if(stage.isMaximized()){
                btnMaximize.setImage(StaticUtil.getIcon("white-minimize-hover.png"));
            }else{
                btnMaximize.setImage(StaticUtil.getIcon("white-maximize-hover.png"));
            }
        }

        if(source.equals(btnResearch)){
            bpResearchPane.toFront();
        }if(source.equals(btnImport)){
            bpImportPane.toFront();
        }if(source.equals(btnExport)){
            bpExportPane.toFront();
        }
    }

    @FXML
    private void handleMouseEnteredEventFromMainScene(MouseEvent event){

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
    private void handleMouseExitedEventFromMainScene(MouseEvent event){

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

    //ResearchPane Event Handlers
    @FXML
    private void handleActionEventFromResearchPane(ActionEvent actionEvent) throws IOException {

        if(actionEvent.getSource() == btnResearchUpdate){
            researchPaneService.updateResearchsVBox();
        }

        if(actionEvent.getSource() == btnResearchAdd){
            researchPaneService.launchResearchAddScreen();
        }
    }

    //QuestionPane Event Handlers
    @FXML
    private void handleActionEventFromQuestionPane(ActionEvent actionEvent) throws IOException {

        if(actionEvent.getSource() == btnQuestionUpdate){
            questionPaneService.updateQuestionsVBox();
        }

        if(actionEvent.getSource() == btnQuestionAdd){
            questionPaneService.launchQuestionAddScreen();
        }

        if(actionEvent.getSource() == btnBackToResearchs){
            bpResearchPane.toFront();
        }
    }

    //AnswerPane Event Handlers
    @FXML
    private void handleActionEventFromAnswerPane(ActionEvent actionEvent) throws IOException {

        if(actionEvent.getSource() == btnBackToQuestions){
            bpQuestionPane.toFront();
        }

        if(actionEvent.getSource() == btnAnswerAdd){
            answerPaneService.launchAnswerAddScreen();
        }
    }

    //ImportPane Event Handlers
    @FXML
    private void handleActionEventFromImportPane(ActionEvent actionEvent){

        if(actionEvent.getSource() == btnImportSelect){
            importPaneService.launchSelectScreen();
        }

        if(actionEvent.getSource() == btnDoImport){
            importPaneService.doImport();
        }

    }

    //ExportPane Event Handlers
    @FXML
    private void handleActionEventFromExportPane(ActionEvent actionEvent){

        if(actionEvent.getSource() == btnExportSelect){
            exportPaneService.launchSelectScreen();
        }

        if(actionEvent.getSource() == btnDoExport){
            exportPaneService.doExport();
        }

    }

    @Override
    public void setStage(Stage stage){

        this.stage = stage;
        stageConfig();
    }

    private void stageConfig(){

        stage.setWidth(StaticUtil.screenWidth * 0.8);
        stage.setHeight(StaticUtil.screenHeight * 0.8);

        stage.centerOnScreen();

        stage.show();
    }

    public VBox getVbResearchs() {
        return vbResearchs;
    }
    public VBox getVbQuestions() {return vbQuestions; }

    public QuestionPaneService getQuestionPaneService(){
        return questionPaneService;
    }

    public BorderPane getBpQuestionPane(){
        return bpQuestionPane;
    }

    public Label getLblResearchTitle() {
        return lblResearchTitle;
    }

    public TextArea getTaResearchDescription() {
        return taResearchDescription;
    }

    public AnswerPaneService getAnswerPaneService() {
        return answerPaneService;
    }

    public BorderPane getBpAnswerPane(){
        return  bpAnswerPane;
    }

    public Label getLblQuestionTitle(){
        return lblQuestionTitle;
    }

    public TableView<Answer> getTvAnswers() {
        return tvAnswers;
    }

    public TextField getTxtFilePath() {
        return txtFilePath;
    }

    public ListView getLvExportResearchs() {
        return lvExportResearchs;
    }

    public TextField getTxtExportPath() {
        return txtExportPath;
    }
}