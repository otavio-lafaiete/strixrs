package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.MainController;
import com.strixrs.csv.ExportCSV;
import com.strixrs.model.Question;
import com.strixrs.model.Research;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import com.strixrs.data.DataResearchs;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        File pathFile = dc.showDialog(controller.getStage());

        if(pathFile != null)
            mainController.getTxtExportPath().setText(pathFile.toString());
    }

    public void doExport(){

        ListView<String> listResearchs =  mainController.getLvExportResearchs();

        String actualResearchName = listResearchs.getSelectionModel().getSelectedItem();

        if(actualResearchName == null){


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Nenhuma pesquisa selecionada");

            alert.showAndWait();

            return;

        }

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

        Path path = Paths.get(mainController.getTxtExportPath().getText());

        if(!path.toFile().exists()){


            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Não foi possível salvar um arquivo no caminho especificado");

            alert.showAndWait();

            return;
        }

        ExportCSV.exportCSV(path.toString(), actualResearchName, sb);
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
