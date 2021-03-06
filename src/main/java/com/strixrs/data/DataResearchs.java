package com.strixrs.data;

import com.strixrs.model.Research;
import com.strixrs.serialization.ReadSXRS;
import com.strixrs.serialization.SaveSXRS;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataResearchs {

    private static List<Research> researchs = new ArrayList<>();
    private static Path researchsPath = Paths.get(System.getProperty("user.dir") +
            "\\src\\main\\resources\\com\\strixrs\\researchs");

    public static List<Research> getResearchs() {
        if (Files.isDirectory(researchsPath))
            loadResearchs();
        else {
            File d = new File(String.valueOf(researchsPath));
            d.mkdir();
        }
        return researchs;
    }

    public static void addResearch(Research research) {

        SaveSXRS.saveResearch(Paths.get(researchsPath + "\\" + research.getTitle() + ".sxrs"), research);
        loadResearchs();
    }

    private static void loadResearchs() {

        researchs.clear();

        DirectoryStream<Path> directoryStream = null;

        try {
            directoryStream = Files.newDirectoryStream(researchsPath);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        if (directoryStream != null) {

            for (Path path : directoryStream) {
                researchs.add(ReadSXRS.readResearch(path));
            }
        }
    }

    public static void deleteResearch(String researchTitle) {

        File researchToBeDeleted = new File(researchsPath + "\\" + researchTitle + ".sxrs");
        if (!researchToBeDeleted.delete()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Ooops, ocorreu um erro ao tentar deletar a pesquisa");

            alert.showAndWait();
        }

    }
}
