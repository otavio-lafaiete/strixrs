package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.CollectAnswerOnlineController;
import com.strixrs.controller.MainController;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Answer;
import com.strixrs.model.Question;
import com.strixrs.model.Research;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollectAnswerOnlineService extends AbstractService {

    public CollectAnswerOnlineService(AbsctractController controller) {
        super(controller);
    }

    public void collect(Research research) throws SQLException {

        CollectAnswerOnlineController collectAnswerOnlineController = (CollectAnswerOnlineController) controller;
        MainController mainController = (MainController) collectAnswerOnlineController.getOnlinePaneService().getController();

        final String DATABASE_URL = mainController.getTxtDBUrl().getText();
        final String DATABASE_USER = mainController.getTxtDBUser().getText();
        final String DATABASE_PASSWORD = mainController.getTxtDBPassword().getText();
        final String SELECT_QUERY = "SELECT * FROM \"Evocacoes\"";

        try (
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
                PreparedStatement deleteStatement =
                        connection.prepareStatement("DELETE FROM \"Evocacoes\"")) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            List<Question> questionsBd = new ArrayList<>();

            for (int i = 1; i <= numberOfColumns; i++)
                questionsBd.add(new Question(metaData.getColumnName(i), null));

            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    questionsBd.get(i - 1).getAnswers().add(new Answer((String) resultSet.getObject(i), questionsBd.get(i - 1)));
                }
            }

            for (Question q : questionsBd) {
                for (Question questionResearch : research.getQuestions()) {
                    if (q.getTitle().equals(questionResearch.getTitle())) {
                        for (Answer a : q.getAnswers()) {
                            a.setQuestion(questionResearch);
                            questionResearch.getAnswers().add(a);
                        }
                    }
                }
            }

            DataResearchs.addResearch(research);
            mainController.getMainControllerService().update();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Transação concluída");
            alert.setContentText("As respostas online foram mescladas com as locais, deseja limmpar o banco de dados?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                int rowCount = deleteStatement.executeUpdate();

                Alert alertDelete = new Alert(Alert.AlertType.INFORMATION);
                alertDelete.setTitle("Banco de Dados Limpo");
                alertDelete.setContentText("O banco de dados foi limpo deletando um total de " + rowCount + " linhas");

                alertDelete.showAndWait();
            }

            collectAnswerOnlineController.getStage().close();

        } catch (SQLException sqlException) {
            throw sqlException;
        }
    }
}
