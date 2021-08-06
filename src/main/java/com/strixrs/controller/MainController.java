package com.strixrs.controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnMaximize;
    @FXML private ImageView btnIconify;
    @FXML private AnchorPane anchorPane;

    @Override
    public void setStage(Stage stage){

        this.stage = stage;
        stageConfig();
    }

    private void stageConfig(){

        Rectangle2D resolution = Screen.getPrimary().getBounds();
        stage.setWidth(resolution.getWidth() * 0.8);
        stage.setHeight(resolution.getHeight() * 0.8);

        stage.centerOnScreen();
    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose))
            System.exit(0);

        if(source.equals(btnIconify))
            stage.setIconified(true);

        if(source.equals(btnMaximize)){
            stage.setMaximized(!stage.isMaximized());
            if(stage.isMaximized()){
                btnMaximize.setImage(getIcon("white-minimize-hover.png"));
            }else{
                btnMaximize.setImage(getIcon("white-maximize-hover.png"));
            }
        }
    }

    @FXML
    private void handleMouseEnteredEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose)){
            btnClose.setImage(getIcon("white-close-hover.png"));
        }

        if(source.equals(btnIconify)){
            btnIconify.setImage(getIcon("white-iconify-hover.png"));
        }

        if(source.equals(btnMaximize)){
            if(stage.isMaximized()){
                btnMaximize.setImage(getIcon("white-minimize-hover.png"));
            }else{
                btnMaximize.setImage(getIcon("white-maximize-hover.png"));
            }
        }
    }

    @FXML
    private void handleMouseExitedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose)){
            btnClose.setImage(getIcon("white-close.png"));
        }

        if(source.equals(btnIconify)){
            btnIconify.setImage(getIcon("white-iconify.png"));
        }

        if(source.equals(btnMaximize)) {
            if(stage.isMaximized()){
                btnMaximize.setImage(getIcon("white-minimize.png"));
            }else{
                btnMaximize.setImage(getIcon("white-maximize.png"));
            }
        }
    }

}
