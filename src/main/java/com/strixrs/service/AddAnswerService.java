package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddAnswerController;
import com.strixrs.controller.AddQuestionController;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Answer;
import com.strixrs.model.Question;
import com.strixrs.model.Research;

public class AddAnswerService extends AbstractService{

    public AddAnswerService(AbsctractController controller) {
        super(controller);
    }

    public void addAnswer(String answer, String id) {

        AddAnswerController addAnswerController = (AddAnswerController) controller;

        Question question = addAnswerController.getAnswerPaneService().getCurrentQuestion();

        Answer answerToBeAdd = new Answer(answer, id, question);
        question.getAnswers().add(answerToBeAdd);

        DataResearchs.addResearch(question.getResearch());

        addAnswerController.getAnswerPaneService().updateAnswers();

        addAnswerController.getStage().close();
    }
}
