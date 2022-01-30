package com.strixrs.controller;

import com.strixrs.service.AnswerPaneService;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditQuestionController extends AbsctractController {

    private AnswerPaneService answerPaneService;
    @FXML
    private TextField txtTitle;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAnswerPaneService(AnswerPaneService answerPaneService) {
        this.answerPaneService = answerPaneService;
    }

    public TextField getTxtTitle() {
        return txtTitle;
    }
}
