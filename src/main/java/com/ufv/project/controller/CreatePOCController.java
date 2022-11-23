package com.ufv.project.controller;

import com.ufv.project.Main;
import com.ufv.project.db.*;
import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreatePOCController
{
    // ---------- MainPane ----------
    @FXML
    private VBox mainPane;
    // ------------------------------

    // ----------- Layout -----------
    @FXML
    private GridPane gridPane;
    // ------------------------------

    // ---------- Top Menu ----------
    @FXML
    private TopMenuController topMenuController;
    // ------------------------------

    // ---- Personal information ----
    @FXML
    private VBox userData;

    @FXML
    private PersonalInfoController userDataController;
    // ------------------------------

    // --------- Create POC ---------
    @FXML
    private TextField title;

    @FXML
    private MenuButton authorMenuButton;

    @FXML
    private ComboBox<Professor> advisorComboBox;

    @FXML
    private MenuButton coAdvisorMenuButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Field> fieldComboBox;

    @FXML
    private ListView<String> keywordList;

    @FXML
    private Button choosePDFButton;

    @FXML
    private Text pdfFilepathText;

    @FXML
    private Button addPOCButton;
    // ------------------------------

    private File pdfFile;

    @FXML
    public void initialize()
    {
        // Disables button until every field has been populated.
        addPOCButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                        title.getText().trim().isEmpty(),
                                title.textProperty())
//                        .or(authorComboBox.valueProperty().isNull())
//                        .or(advisorComboBox.valueProperty().isNull())
//                        .or(coAdvisorComboBox.valueProperty().isNull())
//                        .or(datePicker.valueProperty().isNull())
//                        .or(fieldComboBox.valueProperty().isNull())
//                        .or(Bindings.isEmpty(keywordList.getItems()))
                        .or(Bindings.createBooleanBinding(() ->
                                        pdfFilepathText.getText().trim().isEmpty(),
                                pdfFilepathText.textProperty()))
        );

        // Sets values to top menu.
        topMenuController.setUserRole("Teacher");

        // Sets values according to the current user.
        userDataController.setUsernameText(UserDataSingleton.getInstance().getUsername());
        userDataController.setNameText(UserDataSingleton.getInstance().getName());
        userDataController.setUserTypeText(UserDataSingleton.getInstance().getUserType());

        ObservableList<Professor> professors = null;

        try (ConnectDB connectDB = new ConnectDB();
            ProfessorDB professorDB = new ProfessorDB(connectDB.getConnection());
            StudentDB studentDB = new StudentDB(connectDB.getConnection()))
        {
            professors = FXCollections.observableList(professorDB.getAllProfessors());
            authorMenuButton.getItems().setAll(initializeCheckMenuItemsFromList(studentDB.getAllStudents()));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Set data for choosing.
        if (professors != null)
        {
            advisorComboBox.setItems(professors);
            coAdvisorMenuButton.getItems().addAll(initializeCheckMenuItemsFromList(professors));

        }
    }

    @FXML
    public void handlePOCAdding()
    {
        Main.loadStage("search-poc-page-view.fxml");
        Main.closeCurrentStage(mainPane);
    }

    @FXML
    public void handlePDFChoosing()
    {
        FileChooser chooser = new FileChooser();

        chooser.setTitle("Save PDF File");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        pdfFile = chooser.showOpenDialog(gridPane.getScene().getWindow());

        if (pdfFile != null)
        {
            pdfFilepathText.setText(pdfFile.getName());
        }
        else
        {
            pdfFilepathText.setText("");
        }
    }

    public List<MenuItem> initializeCheckMenuItemsFromList(List<? extends User> userList)
    {
        List<MenuItem> items = new ArrayList<>();

        for (User user : userList)
        {
            CheckMenuItem menuItem = new CheckMenuItem();

            menuItem.setText(user.getName() + " " + user.getUsername());

            items.add(menuItem);
        }

        return items;
    }

}