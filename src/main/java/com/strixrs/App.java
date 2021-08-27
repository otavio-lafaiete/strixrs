package com.strixrs;

import com.strixrs.controller.AbsctractController;
import com.strixrs.staticutil.StaticUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = StaticUtil.getFXML("Login");
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);

        AbsctractController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}