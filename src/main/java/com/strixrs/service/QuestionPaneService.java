package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.MainController;
import com.strixrs.data.DataResearchs;
import com.strixrs.javafxmodfiedcontrol.ResearchButton;
import com.strixrs.model.Question;
import com.strixrs.model.Research;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class QuestionPaneService extends  AbstractService{

    private Research currentResearch;

    public QuestionPaneService(AbsctractController controller) {
        super(controller);
    }

    public void setCurrentResearch(Research currentResearch) {
        this.currentResearch = currentResearch;
    }

    public void updateQuestionsVBox() {

        MainController mainController = (MainController) controller;

        mainController.getVbQuestions().getChildren().clear();
        for(Question question: currentResearch.getQuestions()){

            Button button = new ResearchButton(question.getTitle());
            mainController.getVbQuestions().getChildren().add(button);
        }
    }
}
