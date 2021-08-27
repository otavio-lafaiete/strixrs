package com.strixrs.controller;

import javafx.stage.Stage;

//All controller references their stage
public abstract class AbsctractController {

    Stage stage;

    public abstract void setStage(Stage stage);

    public Stage getStage() {
        return stage;
    }
}
