package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.EditQuestionController;
import com.strixrs.controller.MainController;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Question;

public class EditQuestionService extends AbstractService{

    public EditQuestionService(AbsctractController controller) {
        super(controller);
    }

    public void editQuestion(String title) {

        EditQuestionController controller = (EditQuestionController) this.controller;
        Question question = controller.getAnswerPaneService().getCurrentQuestion();

        question.setTitle(title);

        DataResearchs.addResearch(question.getResearch());

        controller.getStage().close();
        controller.getAnswerPaneService().update();
        MainController mainController = (MainController) controller.getAnswerPaneService().getController();
        mainController.getQuestionPaneService().updateQuestionsVBox();
    }
}
