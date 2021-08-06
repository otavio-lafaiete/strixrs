package com.strixrs.controller;

import com.strixrs.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends AbsctractController{

    @FXML private TextField tfEmail;
    @FXML private TextField tfPassword;
    @FXML private TextField tfEmailR;
    @FXML private TextField tfPasswordR;
    @FXML private Button btnLogin;
    @FXML private Button btnSignUp;
    @FXML private Button btnSignUpR;
    @FXML private Label lblForgotPassword;
    @FXML private Label btnOfflineLogin;
    @FXML private ImageView btnClose;
    @FXML private ImageView btnIconify;
    @FXML private ImageView btnCloseR;
    @FXML private ImageView btnIconifyR;
    @FXML private ImageView btnBackR;
    @FXML private ImageView iconStrixRS;
    @FXML private AnchorPane anchorPane;
    @FXML private Pane pnlLogin;
    @FXML private Pane pnlSignUp;

    @Override
    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void handleButtonEvent(ActionEvent event){

        Object source = event.getSource();

        if(source.equals(btnSignUp))
            pnlSignUp.toFront();
    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event) throws IOException {

        Object source = event.getSource();

        if(source.equals(btnClose) || source.equals(btnCloseR))
            System.exit(0);

        if(source.equals(btnIconify) || source.equals(btnIconifyR))
            stage.setIconified(true);

        if(source.equals(btnBackR))
            pnlLogin.toFront();

        if(source.equals(btnOfflineLogin)){
            App.setRoot("/com/strixrs/view/Main", stage.getScene(), stage);
        }
    }

    @FXML
    private void handleMouseEnteredEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose))
            btnClose.setImage(getIcon("black-close-hover.png"));


        if(source.equals(btnCloseR))
            btnCloseR.setImage(getIcon("black-close-hover.png"));

        if(source.equals(btnIconify))
            btnIconify.setImage(getIcon("black-iconify-hover.png"));

        if(source.equals(btnIconifyR))
            btnIconifyR.setImage(getIcon("black-iconify-hover.png"));

        if(source.equals(btnBackR))
            btnBackR.setImage(getIcon("back-pressed.png"));
    }

    @FXML
    private void handleMouseExitedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose))
            btnClose.setImage(getIcon("black-close.png"));

        if(source.equals(btnCloseR))
            btnCloseR.setImage(getIcon("black-close.png"));

        if(source.equals(btnIconify))
            btnIconify.setImage(getIcon("black-iconify.png"));

        if(source.equals(btnIconifyR))
            btnIconifyR.setImage(getIcon("black-iconify.png"));

        if(source.equals(btnBackR))
            btnBackR.setImage(getIcon("back.png"));
    }

    @FXML
    private void handleMousePressedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(iconStrixRS))
            iconStrixRS.setImage(getIcon("strixrs-pressed.png"));
    }

    @FXML
    private void handleMouseReleasedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(iconStrixRS))
            iconStrixRS.setImage(getIcon("strixrs.png"));
    }

}
