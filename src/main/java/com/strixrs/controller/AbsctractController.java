package com.strixrs.controller;

import com.strixrs.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public abstract class AbsctractController {

    Stage stage;

    public abstract void setStage(Stage stage);

    protected static Image getIcon(String iconName){
        return new Image(String.valueOf(App.class.getResource("/com/strixrs/icon/" + iconName)));
    }
}
