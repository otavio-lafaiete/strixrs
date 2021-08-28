package com.strixrs.controller;

import com.strixrs.service.QuestionPaneService;
import com.strixrs.service.ResearchPaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnMaximize;
    @FXML private ImageView btnIconify;
    @FXML private AnchorPane anchorPane;
    @FXML private Button btnResearch;
    @FXML private BorderPane bpResearchPane;
    @FXML private BorderPane bpQuestionPane;
    @FXML private TextField txtResearchSearch;
    @FXML private Button btnResearchSearch;
    @FXML private Button btnResearchAdd;
    @FXML private Button btnResearchUpdate;
    @FXML private Button btnQuestionAdd;
    @FXML private Button btnQuestionUpdate;
    @FXML private Button btnBackToResearchs;
    @FXML private VBox vbResearchs;
    @FXML private VBox vbQuestions;

    ResearchPaneService researchPaneService;
    QuestionPaneService questionPaneService;

    public void initialize(){

        researchPaneService = new ResearchPaneService(this);
        researchPaneService.updateResearchsVBox();

        questionPaneService = new QuestionPaneService(this);
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
}