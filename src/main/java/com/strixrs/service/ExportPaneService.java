package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.MainController;
import com.strixrs.csv.ExportCSV;
import com.strixrs.model.Question;
import com.strixrs.model.Research;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import com.strixrs.data.DataResearchs;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExportPaneService extends AbstractService {

    private MainController mainController;
    private static String delimiter = ";";

    public ExportPaneService(AbsctractController absctractController) {

        super(absctractController);
        mainController = (MainController) controller;
    }

    public void launchSelectScreen() {

        DirectoryChooser dc = new DirectoryChooser();

        File pathFile = dc.showDialog(controller.getStage());

        if (pathFile != null)
            mainController.getTxtExportPath().setText(pathFile.toString());
    }

    public void doExport() {

        ListView<String> listResearchs = mainController.getLvExportResearchs();

        String actualResearchName = listResearchs.getSelectionModel().getSelectedItem();

        if (actualResearchName == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Nenhuma pesquisa selecionada");

            alert.showAndWait();

            return;
        }

        Research selectedResearch = null;

        for (Research research : DataResearchs.getResearchs()) {
            if (research.getTitle().equals(actualResearchName)) {
                selectedResearch = research;
            }
        }

        if (!selectedResearch.getQuestions().isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (Question question : selectedResearch.getQuestions()) {

                sb.append(question.getTitle() + delimiter);
            }

            if (sb.length() > 0 && sb.charAt(sb.length() - 1) == delimiter.charAt(0)) {
                sb.setCharAt(sb.length() - 1, '\n');
            }

            int listAnswersSize = selectedResearch.getQuestions().get(0).getAnswers().size();

            for (int i = 0; i < listAnswersSize; i++) {

                for (Question question : selectedResearch.getQuestions()) {

                    sb.append(question.getAnswers().get(i).getAnswer() + delimiter);
                }

                if (sb.length() > 0 && sb.charAt(sb.length() - 1) == delimiter.charAt(0)) {
                    sb.setCharAt(sb.length() - 1, '\n');
                }
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            Path path = Paths.get(mainController.getTxtExportPath().getText());

            if (!path.toFile().exists()) {


                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setContentText("N??o foi poss??vel salvar um arquivo no caminho especificado");

                alert.showAndWait();

                return;
            }

            ExportCSV.exportCSV(path.toString(), actualResearchName, sb);
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Pesquisa est?? vazia, n??o possui evoca????es.");

            alert.showAndWait();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transa????o conclu??da");
        alert.setContentText("A pesquisa foi exportada com sucesso!");

        alert.showAndWait();
    }

    public void updateLVResearchs() {

        ListView<String> listResearchs = mainController.getLvExportResearchs();

        listResearchs.getItems().clear();
        List<Research> researchs = DataResearchs.getResearchs();

        for (Research research : researchs) {
            listResearchs.getItems().add(research.getTitle());
        }

        listResearchs.setPrefHeight(researchs.size() * 24 + 2 > 650 ? 650 : researchs.size() * 24 + 2);
    }

    @Override
    public void update() {
        updateLVResearchs();
    }
}
