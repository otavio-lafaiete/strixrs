package com.strixrs.serialization;

import com.strixrs.model.Report;
import com.strixrs.model.Research;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadSXRS {

    private static ObjectInputStream inputStream;

    private static void openFile(Path path) {

        try {
            inputStream = new ObjectInputStream(Files.newInputStream(path));
        } catch (IOException ioException) {
            System.err.println("Error opening the file");
            System.exit(1);
        }
    }

    private static Research readResearch() {

        Research research = null;

        try {
            research = (Research) inputStream.readObject();
        } catch (IOException ioException) {
            System.err.println("Error reading the file");
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Invalid objetc type");
            System.exit(1);
        }

        return research;
    }

    private static Report readReport() {

        Report report = null;

        try {
            report = (Report) inputStream.readObject();
        } catch (IOException ioException) {
            System.err.println("Error reading the file");
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Invalid objetc type");
            System.exit(1);
        }

        return report;
    }

    private static void closeFile() {
        try {
            if (inputStream != null)
                inputStream.close();
        } catch (IOException ioException) {
            System.err.println("Error closing the file");
            System.exit(1);
        }
    }

    public static Research readResearch(Path path) {

        Research research;

        openFile(path);
        research = readResearch();
        closeFile();

        return research;
    }

    public static Report readReport(Path path) {

        Report report;
        openFile(path);
        report = readReport();
        closeFile();

        return report;
    }

}
