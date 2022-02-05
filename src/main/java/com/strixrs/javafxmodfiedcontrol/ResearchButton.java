package com.strixrs.javafxmodfiedcontrol;

import javafx.scene.control.Button;

public class ResearchButton extends Button {

    public ResearchButton(String title){

        super(title);

        setMaxWidth(Double.MAX_VALUE);

        getStylesheets().add("/com/strixrs/css/MainStyle.css");
        getStyleClass().add("researchButtons");
    }
}
