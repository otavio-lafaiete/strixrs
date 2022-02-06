package com.strixrs.controller;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.strixrs.data.DataReports;
import com.strixrs.model.*;
import com.strixrs.service.SpecificReportPaneService;
import com.strixrs.staticutil.StaticUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

public class WordCloudController extends AbsctractController {

    @FXML private ImageView btnClose;
    @FXML private ImageView btnMaximize;
    @FXML private ImageView btnIconify;
    @FXML private VBox vbEvocations;
    @FXML private Button btnGenerateWordCloud;
    @FXML private Button btnSaveReport;
    @FXML private Button btnRemove;
    @FXML private AnchorPane paneCloudWord;

    private double xOffSet;
    private double yOffSet;

    private List<RadioButton> selectedRadioButtons = new ArrayList<>();

    private WordCloud reportComponent;

    private SpecificReportPaneService specificReportPaneService;

    public void initializeVBEvocations() throws IOException {

        stage.show();

        List<Question> questions = reportComponent.getReport().getResearch().getQuestions();

        ArrayList<Question> wordCloudEvocations = reportComponent.getEvocations();

        for (Question question : questions) {

            HBox hBox = new HBox();
            RadioButton radioButton = new RadioButton(question.getTitle());
            radioButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                    if (isNowSelected) {
                        selectedRadioButtons.add(radioButton);
                    } else {
                        selectedRadioButtons.remove(radioButton);
                    }
                }
            });

            hBox.getChildren().add(radioButton);

            if (wordCloudEvocations != null)
                for (Question fourHousesEvocation : wordCloudEvocations) {
                    if (question.getTitle().equals(fourHousesEvocation.getTitle())) {
                        radioButton.setSelected(true);
                    }
                }

            vbEvocations.getChildren().add(hBox);
        }

        generateWordCloud();
    }

    @FXML
    private void handleActionEvent(ActionEvent actionEvent) throws IOException {

        if (actionEvent.getSource() == btnGenerateWordCloud) {
            generateWordCloud();
        }
        if (actionEvent.getSource() == btnSaveReport) {
            saveReport();
        }
        if (actionEvent.getSource() == btnRemove) {
            remove();
        }
    }

    private void generateWordCloud() throws IOException {

        if (selectedRadioButtons.isEmpty())
            return;

        paneCloudWord.getChildren().clear();
        reportComponent.getEvocations().clear();

        for (RadioButton radioButton : selectedRadioButtons) {

            Research research = reportComponent.getReport().getResearch();
            for (Question question : research.getQuestions()) {
                if (question.getTitle().equals(radioButton.getText())) {
                    reportComponent.getEvocations().add(question);
                }
            }
        }

        List<Answer> listAnswers = new ArrayList<>();

        for (Question question : reportComponent.getEvocations()) {
            for (Answer answer : question.getAnswers()) {
                listAnswers.add(answer);
            }
        }

        List<String> diffAnswersString = listAnswers.stream().map(x -> x.getAnswer()).collect(Collectors.toList());

        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(diffAnswersString);
        final Dimension dimension = new Dimension(600, 600);
        final com.kennycason.kumo.WordCloud wordCloud = new com.kennycason.kumo.WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        wordCloud.setBackgroundColor(new Color(0xFFFFFF));
        wordCloud.setBackground(new CircleBackground(300));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0xFF0000), new Color(0x40AAF1), new Color(0xFFFF00), new Color(0x00FF00), new Color(0xF2F2FF)));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
        wordCloud.build(wordFrequencies);
        BufferedImage bf = wordCloud.getBufferedImage();
        WritableImage wr = null;
        if (bf != null) {
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y));
                }
            }
        }

        ImageView imView = new ImageView(wr);
        paneCloudWord.getChildren().add(imView);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        stageConfig();
    }

    private void stageConfig() {

        stage.initStyle(StageStyle.UNDECORATED);

        stage.getScene().setOnMousePressed((MouseEvent event) ->
        {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });

        stage.getScene().setOnMouseDragged((MouseEvent event) ->
        {
            if (!stage.isMaximized() || (stage.getX() != 0 || stage.getY() != 0)) {

                stage.setX(event.getScreenX() - xOffSet);
                stage.setY(event.getScreenY() - yOffSet);
            }
        });

        stage.setWidth(StaticUtil.screenWidth * 0.8);
        stage.setHeight(StaticUtil.screenHeight * 0.8);

        stage.centerOnScreen();

        stage.show();
    }

    @FXML
    private void handleMouseClickedEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(btnClose))
            stage.close();

        if (source.equals(btnIconify)) {
            stage.setIconified(true);
        }

        if (source.equals(btnMaximize)) {
            stage.setMaximized(!stage.isMaximized());
            if (stage.isMaximized()) {
                btnMaximize.setImage(StaticUtil.getIcon("white-minimize-hover.png"));
            } else {
                btnMaximize.setImage(StaticUtil.getIcon("white-maximize-hover.png"));
            }
        }
    }

    @FXML
    private void handleMouseEnteredEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(btnClose)) {
            btnClose.setImage(StaticUtil.getIcon("white-close-hover.png"));
        }

        if (source.equals(btnIconify)) {
            btnIconify.setImage(StaticUtil.getIcon("white-iconify-hover.png"));
        }

        if (source.equals(btnMaximize)) {
            if (stage.isMaximized()) {
                btnMaximize.setImage(StaticUtil.getIcon("white-minimize-hover.png"));
            } else {
                btnMaximize.setImage(StaticUtil.getIcon("white-maximize-hover.png"));
            }
        }
    }

    @FXML
    private void handleMouseExitedEvent(MouseEvent event) {

        Object source = event.getSource();

        if (source.equals(btnClose)) {
            btnClose.setImage(StaticUtil.getIcon("white-close.png"));
        }

        if (source.equals(btnIconify)) {
            btnIconify.setImage(StaticUtil.getIcon("white-iconify.png"));
        }

        if (source.equals(btnMaximize)) {
            if (stage.isMaximized()) {
                btnMaximize.setImage(StaticUtil.getIcon("white-minimize.png"));
            } else {
                btnMaximize.setImage(StaticUtil.getIcon("white-maximize.png"));
            }
        }
    }

    private void saveReport() {
        DataReports.addReport(reportComponent.getReport());
    }

    public void setComponent(WordCloud reportComponent) {
        this.reportComponent = reportComponent;
    }

    private void remove() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão do Componente Atual");
        alert.setContentText("Têm certeza que deseja excluir o componente atual? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            reportComponent.getReport().getComponents().remove(reportComponent);
            saveReport();
            stage.close();
        }

        specificReportPaneService.updateComponentsVBox();
    }

    public void setSpecificReportPaneService(SpecificReportPaneService specificReportPaneService) {
        this.specificReportPaneService = specificReportPaneService;
    }
}
