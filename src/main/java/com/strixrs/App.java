package com.strixrs;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.LoginController;
import com.strixrs.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private double xOffSet = 0;
    private double yOffSet = 0;

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(AbsctractController.loadFXML("/com/strixrs/view/Login", stage));

        stage.initStyle(StageStyle.UNDECORATED);

        scene.setOnMousePressed((MouseEvent event) ->
        {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) ->
        {
            if(!stage.isMaximized() || (stage.getX() != 0 || stage.getY() != 0)){

                stage.setX(event.getScreenX() - xOffSet);
                stage.setY(event.getScreenY() - yOffSet);
            }
            //System.out.println(stage.getX());
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml, Scene scene, Stage stage) throws IOException {
        scene.setRoot(AbsctractController.loadFXML(fxml, stage));
    }

    public static void main(String[] args) {
        launch();
    }

}