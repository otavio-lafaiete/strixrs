package com.strixrs.controller;

import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController extends AbsctractController {

    @FXML
    private Button btnOfflineLogin;
    @FXML
    private ImageView btnClose;
    @FXML
    private ImageView btnIconify;
    @FXML
    private ImageView iconStrixRS;

    private double xOffSet;
    private double yOffSet;

    @FXML
    private void handleButtonEvent(ActionEvent event) throws IOException {

        Object source = event.getSource();

        if (source.equals(btnOfflineLogin)) {
            stage.close();
            StaticUtil.setRoot(StaticUtil.getFXML("Main"), stage.getScene(), stage);
        }
    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event) throws IOException {

        Object source = event.getSource();

        if (source.equals(btnClose))
            System.exit(0);

        if (source.equals(btnIconify))
            stage.setIconified(true);
    }

    @FXML
    private void handleMouseEnteredEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(btnClose))
            btnClose.setImage(StaticUtil.getIcon("black-close-hover.png"));

        if (source.equals(btnIconify))
            btnIconify.setImage(StaticUtil.getIcon("black-iconify-hover.png"));
    }

    @FXML
    private void handleMouseExitedEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(btnClose))
            btnClose.setImage(StaticUtil.getIcon("black-close.png"));

        if (source.equals(btnIconify))
            btnIconify.setImage(StaticUtil.getIcon("black-iconify.png"));
    }

    @FXML
    private void handleMousePressedEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(iconStrixRS))
            iconStrixRS.setImage(StaticUtil.getIcon("strixrs-pressed.png"));
    }

    @FXML
    private void handleMouseReleasedEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(iconStrixRS))
            iconStrixRS.setImage(StaticUtil.getIcon("strixrs.png"));
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

}