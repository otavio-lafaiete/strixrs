package com.strixrs.controller;

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

public class LoginController {

    @FXML private TextField tfEmail;
    @FXML private TextField tfPassword;
    @FXML private TextField tfEmailR;
    @FXML private TextField tfPasswordR;
    @FXML private Button btnLogin;
    @FXML private Button btnSignUp;
    @FXML private Button btnSignUpR;
    @FXML private Label lblForgotPassword;
    @FXML private ImageView btnClose;
    @FXML private ImageView btnMinimize;
    @FXML private ImageView btnCloseR;
    @FXML private ImageView btnMinimizeR;
    @FXML private ImageView btnBackR;
    @FXML private AnchorPane anchorPane;
    @FXML private Pane pnlLogin;
    @FXML private Pane pnlSignUp;
    @FXML private ImageView iconStrixRS;

    @FXML
    private void handleButtonEvent(ActionEvent event){

        Object source = event.getSource();

        if(source.equals(btnSignUp))
            pnlSignUp.toFront();

    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose) || source.equals(btnCloseR))
            System.exit(0);

        if(source.equals(btnMinimize) || source.equals(btnMinimizeR))
            ((Stage) (anchorPane.getScene().getWindow())).setIconified(true);

        if(source.equals(btnBackR))
            pnlLogin.toFront();
    }

    @FXML
    private void handleMouseEnteredEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose) || source.equals(btnCloseR)){
            btnClose.setImage(new ImageView("/com/strixrs/icon/close-hover.png").getImage());
            btnCloseR.setImage(new ImageView("/com/strixrs/icon/close-hover.png").getImage());
        }

        if(source.equals(btnMinimize) || source.equals(btnMinimizeR)){
            btnMinimize.setImage(new Image("/com/strixrs/icon/maximize-hover.png"));
            btnMinimizeR.setImage(new Image("/com/strixrs/icon/maximize-hover.png"));
        }

        if(source.equals(btnBackR))
            btnBackR.setImage(new Image("/com/strixrs/icon/back-pressed.png"));
    }

    @FXML
    private void handleMouseExitedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose) || source.equals(btnCloseR)){
            btnClose.setImage(new ImageView("/com/strixrs/icon/close.png").getImage());
            btnCloseR.setImage(new ImageView("/com/strixrs/icon/close.png").getImage());
        }

        if(source.equals(btnMinimize) || source.equals(btnMinimizeR)){
            btnMinimize.setImage(new ImageView("/com/strixrs/icon/maximize.png").getImage());
            btnMinimizeR.setImage(new ImageView("/com/strixrs/icon/maximize.png").getImage());
        }

        if(source.equals(btnBackR))
            btnBackR.setImage(new Image("/com/strixrs/icon/back.png"));
    }

    @FXML
    private void handleMousePressedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(iconStrixRS))
            iconStrixRS.setImage(new Image("/com/strixrs/icon/strixrs-pressed.png"));
    }

    @FXML
    private void handleMouseReleasedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(iconStrixRS))
            iconStrixRS.setImage(new Image("/com/strixrs/icon/strixrs.png"));
    }

}
