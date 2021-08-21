package com.strixrs.service;

import com.strixrs.data.Researchs;
import com.strixrs.model.Research;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ResearchPaneService {

    private Researchs researchs;

    public ResearchPaneService(Researchs researchs){
        this.researchs = researchs;
    }

    public void updatePane(VBox vBox){

        vBox.getChildren().clear();

        for(Research research: researchs.getResearchs()){

            Button button = new Button(research.getName());
            button.setMaxWidth(Double.MAX_VALUE);
            button.getStylesheets().add("/com/strixrs/css/MainStyle.css");
            button.getStyleClass().add("researchButtons");
            vBox.getChildren().add(button);
        }
    }

}
