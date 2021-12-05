package com.strixrs.service;

import com.strixrs.controller.AbsctractController;
import com.strixrs.controller.MainController;
import com.strixrs.csv.ImportCSV;
import com.strixrs.data.DataResearchs;
import com.strixrs.model.Research;
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
             System.out.println("Erro ao abrir o arquivo");
             return;
         }


         mainController.getTxtFilePath().setText(file.toString());
    }

    public void doImport() {

        if(file == null){
            System.out.println("erro, o arquivo é nulo");
            return;
        }
        Research research = ImportCSV.importCSV(file);
        DataResearchs.addResearch(research);

        mainController.getMainControllerService().update();
    }
}
