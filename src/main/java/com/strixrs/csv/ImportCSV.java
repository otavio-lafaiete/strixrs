package com.strixrs.csv;

import com.strixrs.model.Answer;
import com.strixrs.model.Question;
import com.strixrs.model.Research;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ImportCSV {

    public static Research importCSV(File file){

        Research research = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            research = new Research(file.getName(), "");

            String line = br.readLine();

            String[] arrayQuestions = line.split(",");

            for (String s: arrayQuestions){

                Question question = new Question(s, "", research);

                research.getQuestions().add(question);
            }

            while (line != null) {

                String[] arrayAnswers = line.split(",");

                for(int i = 0; i < arrayQuestions.length; i++){

                    for(Question question: research.getQuestions()){

                        if(question.getTitle().equals(arrayQuestions[i])){

                            Answer answer = new Answer(arrayAnswers[i], "1", question);

                            question.getAnswers().add(answer);
                        }
                    }
                }

                line = br.readLine();
            }
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        for(Question question: research.getQuestions()){
            System.out.println(question.getTitle());
        }

        return research;
    }
}
