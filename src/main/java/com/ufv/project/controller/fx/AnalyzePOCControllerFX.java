package com.ufv.project.controller.fx;

import com.ufv.project.model.DataModel;
import com.ufv.project.model.POC;
import com.ufv.project.model.Professor;
import com.ufv.project.model.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class AnalyzePOCControllerFX
{
    // ---------- Top Menu ----------
    @FXML
    private TopMenuController topMenuController;
    // ------------------------------

    @FXML
    private TextField searchField;

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

    @FXML
    private UpdatePOCController updatePOCControllerFX;

    private URI pdfPath;

    private final DataModel dataModel;

    public AnalyzePOCControllerFX(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize()
    {

    }

    @FXML
    public void onDisplayPDF()
    {
        try
        {
            Desktop.getDesktop().browse(pdfPath);
        }
        catch (IOException e)
        {
            System.out.println("ERROR: Couldn't open PDF: " + e.getMessage());
        }
    }

    public void setData(POC pocToShow)
    {
        titleText.setText(pocToShow.getTitle());
        authorListView.setItems(FXCollections.observableList(pocToShow.getAuthors()));
        advisorText.setText(pocToShow.getAdvisor().toString());
        coAdvisorListView.setItems(FXCollections.observableList(pocToShow.getCoAdvisors()));
        fieldText.setText(pocToShow.getTitle());
        dateText.setText(pocToShow.getDefenseDate().toString());
        keywordList.setItems(FXCollections.observableList(pocToShow.getKeywords()));

        File pdfFile = new File(pocToShow.getPdf().getPath());

        pdfFilepathText.setText(pdfFile.getName());
        pdfPath = pdfFile.toURI();
    }

}