package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddQuestionController;
import com.strixrs.controller.AddResearchController;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Question;
import com.strixrs.model.Research;

public class AddQuestionService extends AbstractService{

    public AddQuestionService(AbsctractController controller) {
        super(controller);
    }

    public void addQuestion(String title) {

        AddQuestionController addQuestionController = (AddQuestionController) controller;

        Research research = addQuestionController.getQuestionPaneService().getCurrentResearch();

        Question question = new Question(title, research);
        research.getQuestions().add(question);

        DataResearchs.addResearch(research);

        addQuestionController.getStage().close();
        addQuestionController.getQuestionPaneService().updateQuestionsVBox();
    }

}
