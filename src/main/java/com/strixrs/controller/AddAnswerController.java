package com.strixrs.controller;

import com.strixrs.service.AddAnswerService;
import com.strixrs.service.AddQuestionService;
import com.strixrs.service.AnswerPaneService;
import com.strixrs.service.QuestionPaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddAnswerController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnIconify;
    @FXML private TextField txtAnswer;
    @FXML private TextField txtID;
    @FXML private Button btnAdd;
    @FXML private Label lblWarning;


    private AddAnswerService addAnswerService;
    private AnswerPaneService answerPaneService;

    private double xOffSet;
    private double yOffSet;

    public void initialize(){

        addAnswerService = new AddAnswerService(this);
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

        if(actionEvent.getSource() == btnAdd){

            String answer = txtAnswer.getText();
            String id = txtID.getText();

            if(answer.isEmpty()){
                lblWarning.setText("A resposta não pode ser vazia");
                txtAnswer.requestFocus();

                return;
            }

            if(id.isEmpty()){
                lblWarning.setText("O ID não pode ser vazio");
                txtID.requestFocus();

                return;
            }

            addAnswerService.addAnswer(answer, id);
        }
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
            if(!stage.isMaximized() || (stage.getX() != 0 || stage.getY() != 0)){

                stage.setX(event.getScreenX() - xOffSet);
                stage.setY(event.getScreenY() - yOffSet);
            }
        });

        stage.centerOnScreen();
    }

    public AnswerPaneService getAnswerPaneService() {
        return answerPaneService;
    }

    public void setAnswerPaneService(AnswerPaneService answerPaneService) {
        this.answerPaneService = answerPaneService;
    }
}
