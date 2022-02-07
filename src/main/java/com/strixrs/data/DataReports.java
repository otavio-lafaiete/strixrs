package com.strixrs.data;

import com.strixrs.model.Report;
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

public class DataReports {

    private static List<Report> reports = new ArrayList<>();
    private static Path reportsPath = Paths.get(System.getProperty("user.dir") +
            "\\src\\main\\resources\\com\\strixrs\\reports");

    public static List<Report> getReports() {
        if (Files.isDirectory(reportsPath))
            loadReports();
        else {
            File d = new File(String.valueOf(reportsPath));
            d.mkdir();
        }
        return reports;
    }

    public static void addReport(Report report) {

        SaveSXRS.saveReport(Paths.get(reportsPath + "\\" + report.getTitle() + ".srep"), report);
        loadReports();
    }

    private static void loadReports() {

        reports.clear();

        DirectoryStream<Path> directoryStream = null;

        try {
            directoryStream = Files.newDirectoryStream(reportsPath);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        if (directoryStream != null) {

            for (Path path : directoryStream) {
                if (!Files.isDirectory(path))
                    reports.add(ReadSXRS.readReport(path));
            }
        }
    }

    public static void deleteReport(String reportName) {

        File reportToBeDeleted = new File(reportsPath + "\\" + reportName + ".srep");
        if (!reportToBeDeleted.delete()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Ooops, ocorreu um erro ao tentar deletar o relatório");

            alert.showAndWait();
        }

    }
}
