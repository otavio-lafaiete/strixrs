package com.strixrs.controller;

import com.strixrs.service.AnswerPaneService;
import com.strixrs.service.EditQuestionService;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditQuestionController extends AbsctractController {

    @FXML
    private TextField txtTitle;
    @FXML
    private Button btnSave;
    @FXML
    private Label lblWarning;
    @FXML
    private ImageView btnClose;
    @FXML
    private ImageView btnIconify;

    private EditQuestionService editQuestionService = new EditQuestionService(this);
    private AnswerPaneService answerPaneService;

    private double xOffSet;
    private double yOffSet;

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

        if (actionEvent.getSource() == btnSave) {

            String title = txtTitle.getText();

            if (title.isEmpty()) {
                lblWarning.setText("O título não pode ser vazio");
                txtTitle.requestFocus();

                return;
            }

            editQuestionService.editQuestion(title);
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        stageConfig();
    }

    private void stageConfig() {

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
            if (!stage.isMaximized() || (stage.getX() != 0 || stage.getY() != 0)) {

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

    public TextField getTxtTitle() {
        return txtTitle;
    }
}
