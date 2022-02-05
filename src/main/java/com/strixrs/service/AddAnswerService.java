package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddAnswerController;
import com.strixrs.controller.MainController;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Answer;
import com.strixrs.model.Question;

import java.util.List;

public class AddAnswerService extends AbstractService{

    public AddAnswerService(AbsctractController controller) {
        super(controller);
    }

    public void addAnswer(String answer, String question) {

        AddAnswerController addAnswerController = (AddAnswerController) controller;

        List<Question> questions = addAnswerController.getQuestionPaneService().getCurrentResearch().getQuestions();

        for (Question questionAux: questions){

            if(questionAux.getTitle().equals(question)){

                Answer answerToBeAdd = new Answer(answer, questionAux);
                questionAux.getAnswers().add(answerToBeAdd);
                DataResearchs.addResearch(questionAux.getResearch());
                addAnswerController.getStage().close();
            }
        }

        MainController mainController = (MainController) addAnswerController.getQuestionPaneService().getController();
        mainController.getMainControllerService().update();
    }
}
