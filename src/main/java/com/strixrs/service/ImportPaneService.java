package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.MainController;
import com.strixrs.csv.ImportCSV;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Research;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class ImportPaneService extends AbstractService{

    MainController mainController;
    File file;

    public ImportPaneService(AbsctractController controller) {

        super(controller);
        mainController = (MainController) controller;
    }

    public void launchSelectScreen() {

        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV File", "*csv"));

         file = fc.showOpenDialog(controller.getStage());

         if(file == null){
             return;
         }


         mainController.getTxtFilePath().setText(file.toString());
    }

    public void doImport() {

        File fileToBeImported = new File(mainController.getTxtFilePath().getText());
        if(!fileToBeImported.exists()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Não foi possível encontrar um arquivo no caminho especificado");

            alert.showAndWait();

            return;
        }

        Research research = ImportCSV.importCSV(fileToBeImported);
        DataResearchs.addResearch(research);

        mainController.getMainControllerService().update();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Transação concluída");
        alert.setContentText("A pesquisa foi importada com sucesso!");

        alert.showAndWait();
    }
}
