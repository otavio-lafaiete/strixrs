package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddResearchController;
import com.strixrs.controller.MainController;
import com.strixrs.data.DataResearchs;
import com.strixrs.javafxmodfiedcontrol.ResearchButton;
import com.strixrs.model.Research;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ResearchPaneService extends AbstractService {

    public ResearchPaneService(AbsctractController controller) {
        super(controller);
    }

    public void updateResearchsVBox() {

        MainController mainController = (MainController) controller;

        mainController.getVbResearchs().getChildren().clear();
        for (Research research : DataResearchs.getResearchs()) {

            Button button = new ResearchButton(research.getTitle());
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    mainController.getQuestionPaneService().setCurrentResearch(research);
                    mainController.getBpQuestionPane().toFront();
                    mainController.getQuestionPaneService().updateQuestionsVBox();
                }
            });
            mainController.getVbResearchs().getChildren().add(button);
        }
    }

    public void launchResearchAddScreen() throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = StaticUtil.getFXML("AddResearch");

        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);

        stage.setScene(scene);

        AddResearchController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setResearchPaneService(this);

        stage.showAndWait();
    }


    @Override
    public void update() {
        updateResearchsVBox();
    }
}
