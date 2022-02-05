package com.strixrs.controller;

import com.strixrs.data.DataResearchs;
import com.strixrs.model.Research;
import com.strixrs.service.CollectAnswerOnlineService;
import com.strixrs.service.OnlinePaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.util.List;

public class CollectAnswerOnlineController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnIconify;
    @FXML private VBox vbResearchs;
    @FXML private Button btnAdd;
    @FXML private Label lblWarning;

    CollectAnswerOnlineService collectAnswerOnlineService;
    OnlinePaneService onlinePaneService;

    private double xOffSet;
    private double yOffSet;

    private ToggleGroup toggleGroup = new ToggleGroup();

    public void initializeScreenComponents(){

        collectAnswerOnlineService = new CollectAnswerOnlineService(this);

        List<Research> researchs = DataResearchs.getResearchs();

        if(researchs.isEmpty()) {
            Label label = new Label("Não há nenhuma pesquisa criada, crie uma para depois coletar as respostas.");
            label.setTextFill(Color.color(1,0,0));
            vbResearchs.getChildren().add(label);
            return;
        }
        RadioButton rb = new RadioButton(researchs.get(0).getTitle());
        rb.setUserData(researchs.get(0));
        rb.setToggleGroup(toggleGroup);
        rb.setSelected(true);
        vbResearchs.getChildren().add(rb);

        researchs.remove(0);

        for(Research research: researchs){

            RadioButton radioButton = new RadioButton(research.getTitle());
            radioButton.setUserData(research);
            radioButton.setToggleGroup(toggleGroup);

            vbResearchs.getChildren().add(radioButton);
        }
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

            if(toggleGroup.getToggles().isEmpty()){
                stage.close();
                return;
            }

            Research research = (Research) toggleGroup.getSelectedToggle().getUserData();

            try {
                collectAnswerOnlineService.collect(research);
            } catch (SQLException e) {
                lblWarning.setText("Erro ao se conectar com o banco de dados.");
            }
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

    public void setOnlinePaneService(OnlinePaneService onlinePaneService) {
        this.onlinePaneService = onlinePaneService;
    }

    public OnlinePaneService getOnlinePaneService() {
        return onlinePaneService;
    }
}
