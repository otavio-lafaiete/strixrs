package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddResearchController;
import com.strixrs.controller.MainController;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Research;

public class AddResearchService extends AbstractService {

    DataResearchs dataResearchs;

    public AddResearchService(AbsctractController controller){

        super(controller);
        dataResearchs = new DataResearchs();
    }

    public void addResearch(String title, String description){

        Research research = new Research(title, description);

        dataResearchs.addResearch(research);

        AddResearchController addResearchController = (AddResearchController) controller;
        addResearchController.getStage().close();
        addResearchController.getResearchPaneService().updateResearchsVBox();
    }
}
