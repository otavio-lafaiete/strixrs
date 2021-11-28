package com.strixrs.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ExportCSV {

        public static boolean exportCSV(String path, String name, StringBuilder toBeWrite){

            try (PrintWriter writer = new PrintWriter(new File(path + "\\" + name + ".csv"))) {

                writer.write(toBeWrite.toString());
            } catch (FileNotFoundException e) {
                return false;
            }

            return true;
        }
}
