package com.ufv.project.controller;

import com.ufv.project.Main;
import com.ufv.project.db.*;
import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private TextArea summaryTextArea;

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

    @FXML
    private ProgressIndicator progressIndicator;
    // ------------------------------

    private File pdfFile;

    public static final String PDF_STORAGE_FOLDER = "src" + File.separator +
            "main" + File.separator +
            "resources" + File.separator +
            "com" + File.separator +
            "ufv" + File.separator +
            "project" + File.separator + "pdfs" + File.separator;

    @FXML
    public void initialize()
    {
        // Disables button until every field has been populated.
//        addPOCButton.disableProperty().bind(
//                Bindings.createBooleanBinding(() ->
//                                        title.getText().trim().isEmpty(),
//                                title.textProperty())
////                        .or(authorComboBox.valueProperty().isNull())
////                        .or(advisorComboBox.valueProperty().isNull())
////                        .or(coAdvisorComboBox.valueProperty().isNull())
////                        .or(datePicker.valueProperty().isNull())
////                        .or(fieldComboBox.valueProperty().isNull())
////                        .or(Bindings.isEmpty(keywordList.getItems()))
//                        .or(Bindings.createBooleanBinding(() ->
//                                        pdfFilepathText.getText().trim().isEmpty(),
//                                pdfFilepathText.textProperty()))
//        );

        // Sets values to top menu.
        topMenuController.setUserRole(changeCase(UserDataSingleton.getInstance().getUserType()));

        // Sets values according to the current user.
        userDataController.setUsernameText(UserDataSingleton.getInstance().getUsername());
        userDataController.setNameText(UserDataSingleton.getInstance().getName());
        userDataController.setUserTypeText(changeCase(UserDataSingleton.getInstance().getUserType()));

        ObservableList<Professor> professors = null;

        try (ConnectDB connectDB = new ConnectDB())
        {
            professors = FXCollections.observableList(new ProfessorDB(connectDB.getConnection()).getAllProfessors());
            authorMenuButton.getItems().setAll(initializeCheckMenuItemsFromList(new StudentDB(connectDB.getConnection()).getAllStudents()));
            coAdvisorMenuButton.getItems().setAll(initializeCheckMenuItemsFromList(professors));
            fieldComboBox.setItems(FXCollections.observableList(new FieldDB(connectDB.getConnection()).queryFields()));
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
        final Task<Integer> task = new Task<>()
        {
            @Override
            protected Integer call() throws SQLException, IOException
            {
                try (ConnectDB connectDB = new ConnectDB())
                {
                    String pdfFilepath = PDF_STORAGE_FOLDER + pdfFile.getName();

                    Files.copy(Paths.get(pdfFile.getAbsolutePath()),
                            Paths.get(pdfFilepath),
                            StandardCopyOption.COPY_ATTRIBUTES,
                            StandardCopyOption.REPLACE_EXISTING);

                    List<Student> authors = new ArrayList<>();

                    for (MenuItem item : authorMenuButton.getItems())
                    {
                        if (((CheckMenuItem) item).isSelected())
                        {
                            authors.add((Student) new UserDB(connectDB.getConnection()).queryUserByID(item.getId()));
                        }
                    }

                    List<Professor> coAdvisors = new ArrayList<>();

                    for (MenuItem item : coAdvisorMenuButton.getItems())
                    {
                        if (((CheckMenuItem) item).isSelected())
                        {
                            coAdvisors.add((Professor) new UserDB(connectDB.getConnection()).queryUserByID(item.getId()));
                        }
                    }

                    return new POCDB(connectDB.getConnection()).insertPOC(new POC(0,
                            title.getText(),
                            authors,
                            datePicker.getValue(),
                            keywordList.getItems().stream().toList(),
                            summaryTextArea.getText(),
                            fieldComboBox.getValue(),
                            new PDF(0, pdfFilepath, LocalDate.now()),
                            ((Professor) new UserDB(connectDB.getConnection()).queryUserByID(UserDataSingleton.getInstance().getUsername())),
                            advisorComboBox.getValue(),
                            coAdvisors
                    ));
                }
            }
        };

        task.setOnSucceeded(workerStateEvent ->
        {
            Main.loadStage("search-poc-page-view.fxml", "Search POC");
            Main.closeCurrentStage(mainPane);
        });

        task.setOnFailed(workerStateEvent ->
        {
            if (task.getException() != null)
            {
                task.getException().printStackTrace();
            }
        });

        new Thread(task).start();
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        addPOCButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));

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

            menuItem.setId(user.getUsername());
            menuItem.setText(user.getName() + " " + user.getUsername());

            items.add(menuItem);
        }

        return items;
    }

    public String changeCase(String string)
    {
        if (string == null)
        {
            return null;
        }

        return string.charAt(0) + string.substring(1).toLowerCase();
    }

}