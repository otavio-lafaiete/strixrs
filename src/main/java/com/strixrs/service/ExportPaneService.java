package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.MainController;
import com.strixrs.csv.ExportCSV;
import com.strixrs.model.Question;
import com.strixrs.model.Research;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import com.strixrs.data.DataResearchs;

import java.util.ArrayList;
import java.util.List;

public class ExportPaneService extends AbstractService{

    MainController mainController;

    public ExportPaneService(AbsctractController absctractController) {

        super(absctractController);
        mainController = (MainController) controller;
    }

    public void launchSelectScreen() {

        DirectoryChooser dc = new DirectoryChooser();

        mainController.getTxtExportPath().setText(dc.showDialog(controller.getStage()).toString());
    }

    public void doExport(){

        ListView<String> listResearchs =  mainController.getLvExportResearchs();

        String actualResearchName = listResearchs.getSelectionModel().getSelectedItem();

        Research selectedResearch = null;

        for(Research research: DataResearchs.getResearchs()){
            if(research.getTitle().equals(actualResearchName)){
                selectedResearch = research;
            }
        }

        StringBuilder sb = new StringBuilder();

        for(Question question: selectedResearch.getQuestions()){

            sb.append(question.getTitle() + ",");
        }

        if(sb.length() > 0 && sb.charAt(sb.length() - 1) == ','){
            sb.setCharAt(sb.length() - 1, '\n');
        }

        int listAnswersSize = selectedResearch.getQuestions().get(0).getAnswers().size();

        for(int i = 0; i < listAnswersSize; i++){

            for(Question question: selectedResearch.getQuestions()){

                sb.append(question.getAnswers().get(i).getAnswer() + ",");
            }

            if(sb.length() > 0 && sb.charAt(sb.length() - 1) == ','){
                sb.setCharAt(sb.length() - 1, '\n');
            }
        }

        if(sb.length() > 0){
            sb.deleteCharAt(sb.length() - 1);
        }

        ExportCSV.exportCSV(mainController.getTxtExportPath().getText(), actualResearchName, sb);
    }

    public void updateLVResearchs(){

        ListView<String> listResearchs =  mainController.getLvExportResearchs();

        listResearchs.getItems().clear();
        List<Research> researchs = DataResearchs.getResearchs();

        for(Research research: researchs){
            listResearchs.getItems().add(research.getTitle());
        }
    }

    @Override
    public void update(){
        updateLVResearchs();
    }
}
