package com.strixrs.controller;

import com.strixrs.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbsctractController {

    Stage stage;

    public abstract void setStage(Stage stage);

    protected static Image getIcon(String iconName){
        return new Image(String.valueOf(App.class.getResource("/com/strixrs/icon/" + iconName)));
    }

    public static Parent loadFXML(String fxml, Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();

        AbsctractController controller = fxmlLoader.getController();
        controller.setStage(stage);

        return parent;
    }
}
