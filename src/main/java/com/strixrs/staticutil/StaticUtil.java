package com.strixrs.staticutil;

import com.strixrs.App;
import com.strixrs.controller.AbsctractController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class StaticUtil {

    private static final String iconsPath = "/com/strixrs/icon/";
    private static final String fxmlPath = "/com/strixrs/view/";
    public static final Double screenWidth = Screen.getPrimary().getBounds().getWidth();
    public static final Double screenHeight = Screen.getPrimary().getBounds().getHeight();

    public static void setRoot(FXMLLoader fxmlLoader, Scene scene, Stage stage) throws IOException {

        Parent parent = fxmlLoader.load();

        AbsctractController controller = fxmlLoader.getController();
        controller.setStage(stage);

        scene.setRoot(parent);
    }

    public static Image getIcon(String iconName) {
        return new Image(String.valueOf(App.class.getResource(iconsPath + iconName)));
    }

    public static FXMLLoader getFXML(String name) {
        return new FXMLLoader(App.class.getResource(fxmlPath + name + ".fxml"));
    }
}
