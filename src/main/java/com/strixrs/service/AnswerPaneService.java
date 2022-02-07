package com.strixrs.service;

import com.strixrs.controller.*;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Answer;
import com.strixrs.model.Question;
import com.strixrs.staticutil.StaticUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AnswerPaneService extends AbstractService {

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

    public void launchQuestionEditScreen() throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = StaticUtil.getFXML("EditQuestion");

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
    }

    public void deleteCurrentQuestion() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão da evocação " + currentQuestion.getTitle());
        alert.setContentText("Têm certeza que deseja excluir a evocação atual? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            currentQuestion.getResearch().getQuestions().remove(currentQuestion);
            DataResearchs.addResearch(currentQuestion.getResearch());
            MainController mainController = (MainController) controller;
            mainController.getMainControllerService().update();
            mainController.getBpQuestionPane().toFront();
        }
    }

    @Override
    public void update() {
        updateAnswers();
    }

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
