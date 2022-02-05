package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.AddAnswerOnlineController;
import com.strixrs.controller.MainController;
import com.strixrs.model.Question;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.List;

public class AddAnswerOnlineService extends AbstractService{

    public AddAnswerOnlineService(AbsctractController controller) {
        super(controller);
    }

    public void addAnswer(List<Question> questions) throws SQLException {

        AddAnswerOnlineController addAnswerOnlineController = (AddAnswerOnlineController) controller;
        MainController mainController = (MainController) addAnswerOnlineController.getOnlinePaneService().getController();

        final String DATABASE_URL = mainController.getTxtDBUrl().getText();
        final String DATABASE_USER = mainController.getTxtDBUser().getText();
        final String DATABASE_PASSWORD = mainController.getTxtDBPassword().getText();

        try (
                Connection connection = DriverManager.getConnection(
                        DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD))
        {
            String stringParams = "";
            for(Question q: questions){
                stringParams += "?, ";
            }

            char[] charArray = stringParams.toCharArray();
            stringParams = "";

            for(int i = 0; i < charArray.length - 2; i++){
                stringParams = stringParams.concat(String.valueOf(charArray[i]));
            }

            System.out.println("INSERT INTO \"Evocacoes\" (" + stringParams + ") VALUES ("  + stringParams + ")");

            int paramIndex = 1;
            String columnNames = "";
            for (int i = 0; i < questions.size(); i++){
                columnNames = columnNames.concat(questions.get(i).getTitle());
                if(i == questions.size() - 1)
                    break;
                columnNames = columnNames.concat(", ");
            }

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO \"Evocacoes\" (" + columnNames + ") VALUES ("  + stringParams + ")");

            for (Question questionAux: questions){
                preparedStatement.setString(paramIndex, questionAux.getAnswers().get(0).getAnswer());
                paramIndex++;
            }

            preparedStatement.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Transação concluída");
            alert.setContentText("Suas respostas foram incluídas com sucesso!");

            alert.showAndWait();

            addAnswerOnlineController.getStage().close();
        }
        catch (SQLException sqlException)
        {
            throw sqlException;
        }

    }
}
