package com.ufv.project.controller;

import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;

public class CreatePOCController
{
    @FXML
    private GridPane gridPane;

    @FXML
    private TextField title;

    @FXML
    private ComboBox<Student> authorComboBox;

    @FXML
    private ComboBox<Professor> advisorComboBox;

    @FXML
    private ComboBox<Professor> coAdvisorComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Field> fieldComboBox;

    @FXML
    private ListView<String> keywordList;

    @FXML
    private Button addPOCButton;

    @FXML
    public void initialize()
    {
        // Disables button until every field has been populated.
        addPOCButton.disableProperty().bind(
                title.textProperty().isEmpty()
                        .or(authorComboBox.valueProperty().isNull())
                        .or(advisorComboBox.valueProperty().isNull())
                        .or(coAdvisorComboBox.valueProperty().isNull())
                        .or(datePicker.valueProperty().isNull())
                        .or(fieldComboBox.valueProperty().isNull())
                        .or(Bindings.isEmpty(keywordList.getItems()))
        );
    }


    @FXML
    public void handlePOCAdding()
    {

    }

    @FXML
    public void handlePDFChoosing()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save PDF File");

        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File file = chooser.showOpenDialog(gridPane.getScene().getWindow());

        if (file != null)
        {
            System.out.println(file.getName());
        }
    }

    @FXML
    public boolean handlePOCUpdate()
    {


        return false;
    }

    public boolean addCoAdvisorToPOC(String studentUsername, Professor newCoAdvisor)
    {
        return false;
    }

    public boolean removeCoAdvisorFromPOC(String studentUsername, Professor newCoAdvisor)
    {
        return false;
    }

    public boolean removePOC(String studentUsername)
    {
        return false;
    }

}
