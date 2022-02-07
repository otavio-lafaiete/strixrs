package com.strixrs.controller;

import com.strixrs.model.Question;
import com.strixrs.service.AddQuestionService;
import com.strixrs.service.QuestionPaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddQuestionController extends AbsctractController {

    @FXML
    private ImageView btnClose;
    @FXML
    private ImageView btnIconify;
    @FXML
    private TextField txtTitle;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblWarning;

    private AddQuestionService addQuestionService;
    private QuestionPaneService questionPaneService;

    private double xOffSet;
    private double yOffSet;

    public void initialize() {

        addQuestionService = new AddQuestionService(this);
    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(btnClose))
            stage.close();


        if (source.equals(btnIconify))
            stage.setIconified(true);
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
    }

    @FXML
    private void handleActionEvent(ActionEvent actionEvent) {

        if (actionEvent.getSource() == btnAdd) {

            String title = txtTitle.getText();

            if (title.isEmpty()) {
                lblWarning.setText("O título não pode ser vazio");
                txtTitle.requestFocus();

                return;
            }

            for (Question question : questionPaneService.getCurrentResearch().getQuestions()) {
                if (question.getTitle().equals(title)) {

                    lblWarning.setText("Já existe uma evocação com esse título");
                    txtTitle.requestFocus();
                    return;
                }
            }

            addQuestionService.addQuestion(title);
        }
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

        stage.centerOnScreen();
    }

    public void setQuestionPaneService(QuestionPaneService questionPaneService) {
        this.questionPaneService = questionPaneService;
    }

    public QuestionPaneService getQuestionPaneService() {
        return questionPaneService;
    }
}
