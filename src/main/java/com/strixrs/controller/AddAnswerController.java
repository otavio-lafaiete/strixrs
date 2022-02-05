package com.strixrs.controller;

import com.strixrs.model.Question;
import com.strixrs.service.AddAnswerService;
import com.strixrs.service.AnswerPaneService;
import com.strixrs.service.QuestionPaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class AddAnswerController extends AbsctractController{

    @FXML private ImageView btnClose;
    @FXML private ImageView btnIconify;
    @FXML private VBox vbAnswers;
    @FXML private Button btnAdd;
    @FXML private Label lblWarning;

    ArrayList<Label> labels;
    ArrayList<TextField> textFields;

    QuestionPaneService questionPaneService;
    AddAnswerService addAnswerService;

    private double xOffSet;
    private double yOffSet;

    public void initializeScreenComponents(){

        addAnswerService = new AddAnswerService(this);

        labels = new ArrayList<>();
        textFields = new ArrayList<>();

        for(Question question: questionPaneService.getCurrentResearch().getQuestions()){
            Label label = new Label(question.getTitle());
            vbAnswers.getChildren().add(label);
            labels.add(label);
            TextField textField = new TextField();
            vbAnswers.getChildren().add(textField);
            textFields.add(textField);
            vbAnswers.getChildren().add(new Separator());
        }
    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose))
            stage.close();


        if(source.equals(btnIconify))
            stage.setIconified(true);
    }

    @FXML
    private void handleMouseEnteredEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose)){
            btnClose.setImage(StaticUtil.getIcon("white-close-hover.png"));
        }

        if(source.equals(btnIconify)){
            btnIconify.setImage(StaticUtil.getIcon("white-iconify-hover.png"));
        }
    }

    @FXML
    private void handleMouseExitedEvent(MouseEvent event){

        Object source = event.getSource();

        if(source.equals(btnClose)){
            btnClose.setImage(StaticUtil.getIcon("white-close.png"));
        }

        if(source.equals(btnIconify)){
            btnIconify.setImage(StaticUtil.getIcon("white-iconify.png"));
        }
    }

    @FXML
    private void handleActionEvent(ActionEvent actionEvent){

        if(actionEvent.getSource() == btnAdd){

            List<String> answers = new ArrayList<>();
            List<String> questions = new ArrayList<>();

            for(int i = 0; i < textFields.size(); i++){

                String answer = textFields.get(i).getText();
                if(answer.isBlank()){
                    lblWarning.setText("A resposta não pode ser nula");
                    return;
                }

                String question = labels.get(i).getText();

                answers.add(answer);
                questions.add(question);
            }

            for(int i = 0; i < textFields.size(); i++){
               addAnswerService.addAnswer(answers.get(i), questions.get(i));
            }

        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        stageConfig();
    }

    private void stageConfig(){

        stage.initStyle(StageStyle.UNDECORATED);

        stage.getScene().setOnMousePressed((MouseEvent event) ->
        {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        stage.getScene().setOnMouseDragged((MouseEvent event) ->
        {
            if(!stage.isMaximized() || (stage.getX() != 0 || stage.getY() != 0)){

                stage.setX(event.getScreenX() - xOffSet);
                stage.setY(event.getScreenY() - yOffSet);
            }
        });

        stage.centerOnScreen();
    }

    public void setQuestionPaneService(QuestionPaneService questionPaneService) {
        this.questionPaneService = questionPaneService;
    }

    public QuestionPaneService getQuestionPaneService() {
        return questionPaneService;
    }
}
