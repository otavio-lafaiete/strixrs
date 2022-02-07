package com.strixrs.controller;

import javafx.stage.Stage;

public abstract class AbsctractController {

    protected Stage stage;

    public abstract void setStage(Stage stage);

    public Stage getStage() {
        return stage;
    }
}
