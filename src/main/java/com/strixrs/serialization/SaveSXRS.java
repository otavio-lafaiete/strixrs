package com.strixrs.serialization;

import com.strixrs.model.Report;
import com.strixrs.model.Research;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveSXRS {

    private static ObjectOutputStream outputStream;

    private static void openFile(Path path){

        try{
            outputStream = new ObjectOutputStream(Files.newOutputStream(path));
        }catch(IOException ioException){
            System.err.println("Error opening the file");
            System.exit(1);
        }
    }

    private static void saveFile(Research research){

        try{
            outputStream.writeObject(research);
        }catch (IOException ioException){
            System.err.println("Error writing to the file");
            System.exit(1);
        }
    }

    private static void saveFile(Report report){

        try{
            outputStream.writeObject(report);
        }catch (IOException ioException){
            System.err.println("Error writing to the file");
            System.exit(1);
        }
    }

    private static void closeFile(){
        try{
            if(outputStream != null)
                outputStream.close();
        }catch (IOException ioException){
            System.err.println("Error closing the file");
            System.exit(1);
        }
    }

    public static void saveResearch(Path path, Research research){

        openFile(path);
        saveFile(research);
        closeFile();
    }

    public static void saveReport(Path path, Report report) {

        openFile(path);
        saveFile(report);
        closeFile();
    }
}
