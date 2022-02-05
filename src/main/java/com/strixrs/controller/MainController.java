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
import java.sql.SQLException;

public class MainController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnMaximize;
    @FXML private ImageView btnIconify;
    @FXML private Button btnResearch;
    @FXML private Button btnImport;
    @FXML private Button btnExport;
    @FXML private Button btnReport;
    @FXML private BorderPane bpResearchPane;
    @FXML private BorderPane bpQuestionPane;
    @FXML private BorderPane bpAnswerPane;
    @FXML private BorderPane bpImportPane;
    @FXML private BorderPane bpExportPane;
    @FXML private BorderPane bpReportPane;
    @FXML private BorderPane bpSpecificReportPane;
    @FXML private Button btnResearchAdd;
    @FXML private Button btnResearchUpdate;
    @FXML private Button btnQuestionAdd;
    @FXML private Button btnQuestionUpdate;
    @FXML private Button btnQuestionDelete;
    @FXML private Button btnBackToResearchs;
    @FXML private Button btnResearchEdit;
    @FXML private Button btnReportAdd;
    @FXML private Button btnReportUpdate;
    @FXML private VBox vbResearchs;
    @FXML private VBox vbReports;
    @FXML private VBox vbQuestions;
    @FXML private VBox vbReportComponent;
    @FXML private Label lblResearchTitle;
    @FXML private TextArea taResearchDescription;
    @FXML private Label lblQuestionTitle;
    @FXML private Button btnBackToQuestions;
    @FXML private TableView<Answer> tvAnswers;
    @FXML private TableColumn<Answer, String> tcAnswer;
    @FXML private Button btnImportSelect;
    @FXML private TextField txtFilePath;
    @FXML private Button btnDoImport;
    @FXML private TextField txtExportPath;
    @FXML private Button btnExportSelect;
    @FXML private Button btnDoExport;
    @FXML private ListView<String> lvExportResearchs;
    @FXML private Button btnComponentAdd;
    @FXML private Button btnQuestionEdit;
    @FXML private Button btnAnswerDelete;
    @FXML private Button btnQuestionAnswers;
    @FXML private Label lblSpecificReport;
    @FXML private TextField txtDBUrl;
    @FXML private TextField txtDBUser;
    @FXML private TextField txtDBPassword;
    @FXML private Button btnDBAnswer;
    @FXML private Button btnDBCollect;
    @FXML private BorderPane bpOnlinePane;
    @FXML private Button btnOnline;
    @FXML private Button btnReportDelete;
    @FXML private Button btnSpecificReportBack;
    @FXML private Button btnSpecificReportUpdate;
    @FXML private Label lblOnlineWarning;

    MainControllerService mainControllerService;
    ResearchPaneService researchPaneService;
    QuestionPaneService questionPaneService;
    AnswerPaneService answerPaneService;
    ImportPaneService importPaneService;
    ExportPaneService exportPaneService;
    ReportPaneService reportPaneService;
    SpecificReportPaneService specificReportPaneService;
    OnlinePaneService onlinePaneService;

    public void initialize(){

        mainControllerService = new MainControllerService(this);

        researchPaneService = new ResearchPaneService(this);

        questionPaneService = new QuestionPaneService(this);

        answerPaneService = new AnswerPaneService(this);

        importPaneService = new ImportPaneService(this);

        exportPaneService = new ExportPaneService(this);

        reportPaneService = new ReportPaneService(this);

        specificReportPaneService = new SpecificReportPaneService(this);

        onlinePaneService = new OnlinePaneService(this);

        mainControllerService.update();

        tcAnswer.setCellValueFactory(new PropertyValueFactory<>("answer"));

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
            mainControllerService.update();
        }if(source.equals(btnImport)){
            bpImportPane.toFront();
        }if(source.equals(btnExport)){
            mainControllerService.update();
            bpExportPane.toFront();
        }if(source.equals(btnReport)){
            bpReportPane.toFront();
            mainControllerService.update();
        }
        if (source.equals(btnOnline)) {
            bpOnlinePane.toFront();
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

        if(actionEvent.getSource() == btnQuestionDelete){
            questionPaneService.deleteCurrentResearch();
        }

        if(actionEvent.getSource() == btnResearchEdit){
            questionPaneService.launchResearchEditScreen();
        }

        if(actionEvent.getSource() == btnQuestionAnswers){
            questionPaneService.launchAddAnswerScreen();
        }
    }

    //AnswerPane Event Handlers
    @FXML
    private void handleActionEventFromAnswerPane(ActionEvent actionEvent) throws IOException {

        if(actionEvent.getSource() == btnBackToQuestions){
            bpQuestionPane.toFront();
        }
        if(actionEvent.getSource() == btnQuestionEdit){
            answerPaneService.launchQuestionEditScreen();
        }
        if(actionEvent.getSource() == btnAnswerDelete){
            answerPaneService.deleteCurrentQuestion();
        }
    }

    //SpecificReportPane Event Handlers
    @FXML
    private void handleActionEventFromSpecificReportPane(ActionEvent actionEvent) throws IOException {

        if(actionEvent.getSource() == btnComponentAdd){
            specificReportPaneService.launchSpecificReportAddScreen();
        }
        if(actionEvent.getSource() == btnReportDelete){
            specificReportPaneService.deleteCurrentReport();
        }
        if(actionEvent.getSource() == btnSpecificReportBack){
            bpReportPane.toFront();
        }
        if(actionEvent.getSource() == btnSpecificReportUpdate){
            specificReportPaneService.update();
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

    //ReportPane Event Handlers
    @FXML
    private void handleActionEventFromReportPane(ActionEvent actionEvent) throws IOException {

        if(actionEvent.getSource() == btnReportUpdate){
            reportPaneService.updateReportsVBox();
        }

        if(actionEvent.getSource() == btnReportAdd){
            reportPaneService.launchReportAddScreen();
        }
    }

    //OnlinePane Event Handlers
    @FXML
    private void handleActionEventFromOnlinePane(ActionEvent actionEvent) throws IOException {

        if(actionEvent.getSource() == btnDBAnswer){
            try {
                onlinePaneService.launchAnswerOnlineScreen();
            } catch (SQLException e) {
                lblOnlineWarning.setText("Erro ao se conectar com o banco de dados.");
            }
        }

        if(actionEvent.getSource() == btnDBCollect){
            onlinePaneService.launchCollectOnlineScreen();
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

    public ListView<String> getLvExportResearchs() {
        return lvExportResearchs;
    }

    public TextField getTxtExportPath() {
        return txtExportPath;
    }

    public BorderPane getBpResearchPane() {
        return bpResearchPane;
    }

    public ResearchPaneService getResearchPaneService(){
        return researchPaneService;
    }

    public MainControllerService getMainControllerService() {

        return mainControllerService;
    }

    public ExportPaneService getExportPaneService() {
        return exportPaneService;
    }

    public VBox getVbReports() {
        return vbReports;
    }

    public BorderPane getBpSpecificReportPane() {
        return bpSpecificReportPane;
    }

    public SpecificReportPaneService getSpecificReportPaneService() {
        return specificReportPaneService;
    }


    public ReportPaneService getReportPaneService() {
        return reportPaneService;
    }

    public VBox getVbReportComponent() {
        return vbReportComponent;
    }

    public Label getLblSpecificReport() {
        return lblSpecificReport;
    }

    public ImageView getBtnClose() {
        return btnClose;
    }

    public TextField getTxtDBUrl() {
        return txtDBUrl;
    }

    public TextField getTxtDBUser() {
        return txtDBUser;
    }

    public TextField getTxtDBPassword() {
        return txtDBPassword;
    }

    public BorderPane getBpReportPane() {
        return bpReportPane;
    }
}