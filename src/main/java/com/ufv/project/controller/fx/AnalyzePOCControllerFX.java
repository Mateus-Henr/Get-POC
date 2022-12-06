package com.ufv.project.controller.fx;

import com.ufv.project.Main;
import com.ufv.project.model.DataModel;
import com.ufv.project.model.POC;
import com.ufv.project.model.Professor;
import com.ufv.project.model.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class AnalyzePOCControllerFX
{
    @FXML
    private VBox mainPane;

    @FXML
    private Text titleText;

    @FXML
    private ListView<Student> authorListView;

    @FXML
    private Text advisorText;

    @FXML
    private ListView<Professor> coAdvisorListView;

    @FXML
    private Text fieldText;

    @FXML
    private Text dateText;

    @FXML
    private ListView<String> keywordList;

    @FXML
    private Hyperlink pdfFilepathText;

    private URI pdfPath;

    private final DataModel dataModel;

    private POC poc;

    /**
     * Constructor for AnalyzePOCControllerFX.
     *
     * @param dataModel data passed in.
     */
    public AnalyzePOCControllerFX(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    /**
     * Displays file explorer for selecting a PDF.
     */
    @FXML
    public void onDisplayPDF()
    {
        try
        {
            Desktop.getDesktop().browse(pdfPath);
        }
        catch (IOException e)
        {
            new Alert(Alert.AlertType.ERROR,
                    "Couldn't open PDF: " + e.getMessage(),
                    ButtonType.OK).showAndWait();
        }
    }

    /**
     * Closes current stage and shows up new stage.
     */
    @FXML
    public void onUpdatePOCButtonClicked()
    {
        if (poc == null)
        {
            new Alert(Alert.AlertType.ERROR,
                    "Couldn't load POC.").showAndWait();
            return;
        }

        UpdatePOCControllerFX newScene = (UpdatePOCControllerFX) Main.loadNewSceneWithDataModel(mainPane, "update-poc-page-view.fxml", dataModel, "Update POC");

        if (newScene != null)
        {
            newScene.setUpPOCData(poc);
        }
    }

    /**
     * Sets up data for the POC to be analyzed.
     *
     * @param poc poc to set up data.
     */
    public void setUpPOCData(POC poc)
    {
        this.poc = poc;

        titleText.setText(poc.getTitle());
        authorListView.setItems(FXCollections.observableList(poc.getAuthors()));
        advisorText.setText(poc.getAdvisor().toString());
        coAdvisorListView.setItems(FXCollections.observableList(poc.getCoAdvisors()));
        fieldText.setText(poc.getTitle());
        dateText.setText(poc.getDefenseDate().toString());
        keywordList.setItems(FXCollections.observableList(poc.getKeywords()));

        File pdfFile = new File(poc.getPdf().getPath());

        pdfFilepathText.setText(pdfFile.getName());
        pdfPath = pdfFile.toURI();
    }

}