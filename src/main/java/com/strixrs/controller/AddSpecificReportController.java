package com.strixrs.controller;

import com.strixrs.service.AddSpecificReportService;
import com.strixrs.service.SpecificReportPaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AddSpecificReportController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnIconify;
    @FXML private TextField txtName;
    @FXML private Button btnAdd;
    @FXML private Label lblWarning;
    @FXML private VBox vbAddReportResearchs;
    @FXML private ToggleGroup tgComponent;
    @FXML private RadioButton rbFourHouses;

    private double xOffSet;
    private double yOffSet;

    private AddSpecificReportService addSpecificReportService;
    private SpecificReportPaneService specificReportPaneService;

    public void initialize(){

        addSpecificReportService = new AddSpecificReportService(this);

        rbFourHouses.setUserData("FourHouses");
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

            String name = txtName.getText();
            String type = tgComponent.getSelectedToggle().getUserData().toString();

            if(name.isEmpty()){
                lblWarning.setText("O Nome não pode ser vazio");
                txtName.requestFocus();
                return;
            }

            addSpecificReportService.addComponent(name, type);
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

    public SpecificReportPaneService getSpecificReportPaneService() {
        return specificReportPaneService;
    }

    public void setSpecificReportPaneService(SpecificReportPaneService specificReportPaneService) {
        this.specificReportPaneService = specificReportPaneService;
    }
}
