package com.strixrs.service;

import com.strixrs.controller.*;
import com.strixrs.model.Question;
import com.strixrs.model.Research;
import com.strixrs.staticutil.StaticUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OnlinePaneService extends AbstractService{

    public OnlinePaneService(AbsctractController controller) {
        super(controller);
    }

    public void launchAnswerOnlineScreen() throws IOException {

        MainController mainController = (MainController) controller;

        final String DATABASE_URL = mainController.getTxtDBUrl().getText();
        final String DATABASE_USER = mainController.getTxtDBUser().getText();
        final String DATABASE_PASSWORD = mainController.getTxtDBPassword().getText();
        final String SELECT_QUERY = "SELECT * FROM \"Evocacoes\"";

        try (
                Connection connection = DriverManager.getConnection(
                        DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            List<Question> questions = new ArrayList<>();

            for (int i = 1; i <= numberOfColumns; i++)
                questions.add(new Question(metaData.getColumnName(i), new Research("Pesquisa de Respostas Online",
                        "Pesquisa utilizada para guardar respostas online")));

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader fxmlLoader = StaticUtil.getFXML("AddAnswerOnline");

            Parent parent = fxmlLoader.load();

            Scene scene = new Scene(parent);

            stage.setScene(scene);

            AddAnswerOnlineController controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.setOnlinePaneService(this);

            controller.initializeScreenComponents(questions);

            stage.showAndWait();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    public void launchCollectOnlineScreen() throws IOException {

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader fxmlLoader = StaticUtil.getFXML("CollectAnswerOnline");

            Parent parent = fxmlLoader.load();

            Scene scene = new Scene(parent);

            stage.setScene(scene);

            CollectAnswerOnlineController controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.setOnlinePaneService(this);
            controller.initializeScreenComponents();

            stage.showAndWait();
    }
}

