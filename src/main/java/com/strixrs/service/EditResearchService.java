package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddResearchController;
import com.strixrs.controller.EditResearchController;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Research;

public class EditResearchService extends AbstractService{

    public EditResearchService(AbsctractController controller) {
        super(controller);
    }


    public void editResearch(String title, String description) {

        EditResearchController controller = (EditResearchController) this.controller;
        Research research = controller.getQuestionPaneService().getCurrentResearch();

        String oldTitle = research.getTitle();

        research.setTitle(controller.getTxtTitle().getText());
        research.setDescription(controller.getTaDescription().getText());

        if (title.equals(oldTitle))
            DataResearchs.addResearch(research);
        else{
            DataResearchs.addResearch(research);
            DataResearchs.deleteResearch(oldTitle);
        }

        controller.getStage().close();
        controller.getQuestionPaneService().update();
    }
}
