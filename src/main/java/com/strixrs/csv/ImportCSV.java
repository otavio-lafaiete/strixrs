package com.strixrs.csv;

import com.strixrs.model.Answer;
import com.strixrs.model.Question;
import com.strixrs.model.Research;

import java.io.*;
import java.nio.charset.Charset;

public class ImportCSV {

    private static String delimiter = ";";

    public static Research importCSV(File file) {

        Research research = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file, Charset.forName("UTF-8")))) {

            char[] arrayFileNameWithExtension = file.getName().toCharArray();
            char[] fileName = new char[arrayFileNameWithExtension.length - 4];

            for (int i = 0; i < arrayFileNameWithExtension.length - 4; i++) {
                fileName[i] = arrayFileNameWithExtension[i];
            }

            research = new Research(String.valueOf(fileName), "");

            String line = br.readLine();

            String[] arrayQuestions = line.split(delimiter);

            for (String s : arrayQuestions) {

                Question question = new Question(s, research);

                research.getQuestions().add(question);
            }

            line = br.readLine();

            while (line != null) {

                String[] arrayAnswers = line.split(delimiter);

                for (int i = 0; i < arrayQuestions.length; i++) {

                    for (Question question : research.getQuestions()) {

                        if (question.getTitle().equals(arrayQuestions[i])) {

                            Answer answer = new Answer(arrayAnswers[i], question);

                            question.getAnswers().add(answer);
                        }
                    }
                }

                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return research;
    }
}
