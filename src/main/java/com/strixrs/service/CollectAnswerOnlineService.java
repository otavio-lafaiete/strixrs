package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.CollectAnswerOnlineController;
import com.strixrs.controller.MainController;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Answer;
import com.strixrs.model.Question;
import com.strixrs.model.Research;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectAnswerOnlineService extends AbstractService{

    public CollectAnswerOnlineService(AbsctractController controller) {
        super(controller);
    }

    public void collect(Research research) {

        CollectAnswerOnlineController collectAnswerOnlineController = (CollectAnswerOnlineController) controller;
        MainController mainController = (MainController) collectAnswerOnlineController.getOnlinePaneService().getController();

        final String DATABASE_URL = mainController.getTxtDBUrl().getText();
        final String DATABASE_USER = mainController.getTxtDBUser().getText();
        final String DATABASE_PASSWORD = mainController.getTxtDBPassword().getText();
        final String SELECT_QUERY = "SELECT * FROM \"Evocacoes\"";

        try (
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            List<Question> questionsBd = new ArrayList<>();

            for (int i = 1; i <= numberOfColumns; i++)
                questionsBd.add(new Question(metaData.getColumnName(i), null));

            while (resultSet.next())
            {
                for (int i = 1; i <= numberOfColumns; i++) {
                    questionsBd.get(i - 1).getAnswers().add(new Answer((String) resultSet.getObject(i), questionsBd.get(i -1)));
                }
            }

            for(Question q: questionsBd){
                for(Question questionResearch: research.getQuestions()){
                    if(q.getTitle().equals(questionResearch.getTitle())){
                        for(Answer a: q.getAnswers()){
                            a.setQuestion(questionResearch);
                        }
                        questionResearch.getAnswers().addAll(q.getAnswers());
                    }
                }
            }

            DataResearchs.addResearch(research);
            mainController.getMainControllerService().update();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
