package com.strixrs.controller;

import com.strixrs.data.Researchs;
import com.strixrs.service.ResearchPaneService;
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

public class MainController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnMaximize;
    @FXML private ImageView btnIconify;
    @FXML private AnchorPane anchorPane;
    @FXML private Button btnResearch;

    @FXML private BorderPane bpResearchPane;
    @FXML private TextField txtResearchSearch;
    @FXML private Button btnResearchSearch;
    @FXML private Button btnResearchAdd;
    @FXML private Button btnResearchUpdate;
    @FXML private VBox vbResearchs;

    Researchs researchs;

    ResearchPaneService researchPaneService;

    public void initialize(){

        researchs = new Researchs();
        researchPaneService = new ResearchPaneService(researchs);
        researchPaneService.updatePane(vbResearchs);
    }

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

        stage.show();
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
                btnMaximize.setImage(getIcon("white-minimize-hover.png"));
            }else{
                btnMaximize.setImage(getIcon("white-maximize-hover.png"));
            }
        }
    }

    @FXML
    private void handleMouseEnteredEventFromMainScene(MouseEvent event){

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
    private void handleMouseExitedEventFromMainScene(MouseEvent event){

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

    //ResearchPane Event Handlers
    @FXML
    private void handleActionEventFromResearchPane(ActionEvent actionEvent){

        if(actionEvent.getSource() == btnResearchUpdate){
            researchPaneService.updatePane(vbResearchs);
        }
    }


}