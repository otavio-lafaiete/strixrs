package com.strixrs.service;

import com.strixrs.controller.*;
import com.strixrs.javafxmodfiedcontrol.ResearchButton;
import com.strixrs.model.Answer;
import com.strixrs.model.Question;
import com.strixrs.model.Research;
import com.strixrs.staticutil.StaticUtil;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AnswerPaneService extends  AbstractService{

    private Question currentQuestion;
    private ObservableList<Answer> answersData;

    public AnswerPaneService(AbsctractController controller) {
        super(controller);
    }

    public void updateAnswers() {

        MainController mainController = (MainController) controller;

        mainController.getLblQuestionTitle().setText(currentQuestion.getTitle());

        answersData = FXCollections.observableArrayList(currentQuestion.getAnswers());

        mainController.getTvAnswers().setItems(answersData);
    }

    private void launchQuestionEditScreen() throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = StaticUtil.getFXML("EditResearch");

        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);

        stage.setScene(scene);

        EditQuestionController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setAnswerPaneService(this);

        MainController mainController = (MainController) this.controller;

        controller.getTxtTitle().setText(currentQuestion.getTitle());

        stage.showAndWait();

        mainController.getMainControllerService().update();
    };

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public ObservableList<Answer> getAnswersData() {
        return answersData;
    }

}
