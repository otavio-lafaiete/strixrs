package com.strixrs.service;

import com.strixrs.controller.AbsctractController;

public abstract class AbstractService {

    AbsctractController controller;

    public AbstractService(AbsctractController controller) {
        this.controller = controller;
    }

    public AbsctractController getController() {
        return controller;
    }

    public void update(){}
}
