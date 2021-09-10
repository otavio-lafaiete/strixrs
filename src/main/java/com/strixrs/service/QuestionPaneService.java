package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddQuestionController;
import com.strixrs.controller.AddResearchController;
import com.strixrs.controller.MainController;
import com.strixrs.data.DataResearchs;
import com.strixrs.javafxmodfiedcontrol.ResearchButton;
import com.strixrs.model.Question;
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

public class QuestionPaneService extends  AbstractService{

    private Research currentResearch;

    public QuestionPaneService(AbsctractController controller) {
        super(controller);
    }

    public void setCurrentResearch(Research currentResearch) {
        this.currentResearch = currentResearch;
    }

    public Research getCurrentResearch() {
        return currentResearch;
    }

    public void updateQuestionsVBox() {

        MainController mainController = (MainController) controller;

        mainController.getVbQuestions().getChildren().clear();

        for(Question question: currentResearch.getQuestions()){

            Button button = new ResearchButton(question.getTitle());
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    mainController.getAnswerPaneService().setCurrentQuestion(question);
                    mainController.getBpAnswerPane().toFront();
                    mainController.getAnswerPaneService().updateAnswers();
                }
            });
            mainController.getVbQuestions().getChildren().add(button);
        }

        mainController.getLblResearchTitle().setText(currentResearch.getTitle());
        mainController.getTaResearchDescription().setText(currentResearch.getDescription());
    }


    public void launchQuestionAddScreen() throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = StaticUtil.getFXML("AddQuestion");

        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);

        stage.setScene(scene);

        AddQuestionController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setQuestionPaneService(this);

        stage.showAndWait();
    }
}
