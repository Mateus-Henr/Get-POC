package com.ufv.project.controller;

import com.ufv.project.db.Singleton;
import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class CreatePOCController
{
    // ----------- Layout -----------
    @FXML
    private GridPane gridPane;
    // ------------------------------

    // ---- Personal information ----
    @FXML
    private Text usernameText;

    @FXML
    private Text emailText;

    @FXML
    private Text nameText;
    // ------------------------------

    // --------- Create POC ---------
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
    private Button choosePDFButton;

    @FXML
    private Button addPOCButton;
    // ------------------------------

    @FXML
    public void initialize()
    {
        // Sets title to the page.
        ((Stage) gridPane.getScene().getWindow()).setTitle("Create POC");

        // Disables button until every field has been populated.
        addPOCButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                        title.getText().trim().isEmpty(),
                                title.textProperty())
                        .or(authorComboBox.valueProperty().isNull())
                        .or(advisorComboBox.valueProperty().isNull())
                        .or(coAdvisorComboBox.valueProperty().isNull())
                        .or(datePicker.valueProperty().isNull())
                        .or(fieldComboBox.valueProperty().isNull())
                        .or(Bindings.isEmpty(keywordList.getItems()))
        );

        // Sets values according to the current user.
        usernameText.setText("Matt");
        emailText.setText("...@gmail.com");
        nameText.setText("Me");
    }

    @FXML
    public void handlePOCAdding()
    {
        Singleton.getInstance().addPOC(new POC.POCBuilder()
                .title("My")
                .defenseDate(LocalDate.now())
                .advisor(new Professor("matt", "Matt", "kdka", "da", new ArrayList<>()))
                .coAdvisors(new ArrayList<>())
                .registrant(new Professor("matt", "Matt", "kdka", "da", new ArrayList<>()))
                .pdf(new PDF(1, new File(""), LocalDate.now()))
                .field(new Field(1, "sa"))
                .summary("dadasdasdas")
                .keywords(new ArrayList<>())
                .build());
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

}
