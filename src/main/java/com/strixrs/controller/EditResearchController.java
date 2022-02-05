package com.strixrs.controller;

import com.strixrs.service.EditResearchService;
import com.strixrs.service.QuestionPaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditResearchController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnIconify;
    @FXML private TextField txtTitle;
    @FXML private TextArea taDescription;
    @FXML private Button btnSave;
    @FXML private Label lblWarning;

    private EditResearchService editResearchService;
    private QuestionPaneService questionPaneService;

    private double xOffSet;
    private double yOffSet;

    public void initialize(){

        editResearchService = new EditResearchService(this);
    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose))
            stage.close();


        if(source.equals(btnIconify))
            stage.setIconified(true);
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
    }

    @FXML
    private void handleActionEvent(ActionEvent actionEvent){

        if(actionEvent.getSource() == btnSave){

            String title = txtTitle.getText();

            if(title.isEmpty()){
                lblWarning.setText("O título não pode ser vazio");
                txtTitle.requestFocus();

                return;
            }

            String description = taDescription.getText();

            if(description.isEmpty()){
                lblWarning.setText("A descrição não pode ser vazia");
                taDescription.requestFocus();

                return;
            }

            editResearchService.editResearch(title, description);


        }
    }


    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        stageConfig();
    }

    private void stageConfig(){

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setWidth(StaticUtil.screenWidth * 0.5);
        stage.setHeight(StaticUtil.screenHeight * 0.5);

        stage.getScene().setOnMousePressed((MouseEvent event) ->
        {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        stage.getScene().setOnMouseDragged((MouseEvent event) ->
        {
            if(!stage.isMaximized() || (stage.getX() != 0 || stage.getY() != 0)){

                stage.setX(event.getScreenX() - xOffSet);
                stage.setY(event.getScreenY() - yOffSet);
            }
        });

        stage.centerOnScreen();
    }

    public void setQuestionPaneService(QuestionPaneService questionPaneService) {
        this.questionPaneService = questionPaneService;
    }

    public QuestionPaneService getQuestionPaneService() {
        return questionPaneService;
    }

    public TextField getTxtTitle() {
        return txtTitle;
    }

    public TextArea getTaDescription() {
        return taDescription;
    }
}
