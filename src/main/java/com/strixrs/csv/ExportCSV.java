package com.strixrs.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class ExportCSV {

    public static boolean exportCSV(String path, String name, StringBuilder toBeWrite) {

        try (PrintWriter writer = new PrintWriter(new File(path + "\\" + name + ".csv"), Charset.forName("UTF-8"))) {

            writer.write(toBeWrite.toString());
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
