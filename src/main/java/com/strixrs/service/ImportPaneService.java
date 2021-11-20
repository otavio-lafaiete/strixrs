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

    public ImportPaneService(AbsctractController controller) {

        super(controller);
        mainController = (MainController) controller;
    }

    public void launchSelectScreen() {

        FileChooser fc = new FileChooser();

        File file = fc.showOpenDialog(controller.getStage());

        mainController.getTxtFilePath().setText(file.toString());
    }

    public void doImport() {

        System.out.println("CHEGOU AQUI -------------------------------------------------------------");
        File file = new File(mainController.getTxtFilePath().getText());
        System.out.println(file);
        Research research = ImportCSV.importCSV(file);
        DataResearchs.addResearch(research);
    }
}
