package com.ufv.project.controller.fx;

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
import java.util.Arrays;
import java.util.List;

public class CreatePOCControllerFX
{
    @FXML
    private VBox mainPane;

    @FXML
    private GridPane gridPane;

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
    private ComboBox<Professor> registrantComboBox;

    @FXML
    private TextArea keywordsTextArea;

    @FXML
    private Text pdfFilepathText;

    @FXML
    private Button addPOCButton;

    @FXML
    private ProgressIndicator progressIndicator;

    private final DataModel dataModel;

    private File pdfFile;

    public static final int MAX_NUMBER_CHARACTERS = 100;

    public static final String PDF_STORAGE_FOLDER = "src" + File.separator +
            "main" + File.separator +
            "resources" + File.separator +
            "com" + File.separator +
            "ufv" + File.separator +
            "project" + File.separator + "pdfs" + File.separator;

    /**
     * Constructor for CreatePOCControllerFX.
     *
     * @param dataModel data passed in.
     */
    public CreatePOCControllerFX(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    /**
     * Runs upon initialization.
     */
    @FXML
    public void initialize()
    {
        // Disables button until every field has been populated.
        addPOCButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                        title.getText().trim().isEmpty(),
                                title.textProperty())
                        .or(advisorComboBox.valueProperty().isNull())
                        .or(datePicker.valueProperty().isNull())
                        .or(fieldComboBox.valueProperty().isNull())
                        .or(registrantComboBox.valueProperty().isNull())
                        .or(Bindings.createBooleanBinding(() ->
                                                keywordsTextArea.getText().trim().isEmpty(),
                                        keywordsTextArea.textProperty())
                                .or(Bindings.createBooleanBinding(() ->
                                                pdfFilepathText.getText().trim().isEmpty(),
                                        pdfFilepathText.textProperty())))
        );

        ObservableList<Professor> professors;

        try (ConnectDB connectDB = new ConnectDB())
        {
            professors = FXCollections.observableList(new ProfessorDB(connectDB.getConnection()).queryProfessors());
            authorMenuButton.getItems().setAll(initializeCheckMenuItemsFromList(new StudentDB(connectDB.getConnection()).querStudents()));
            coAdvisorMenuButton.getItems().setAll(initializeCheckMenuItemsFromList(professors));
            fieldComboBox.setItems(FXCollections.observableList(new FieldDB(connectDB.getConnection()).queryFields()));
        }
        catch (SQLException e)
        {
            new Alert(Alert.AlertType.ERROR,
                    "Couldn't load data from database: " + e.getMessage(),
                    ButtonType.OK).showAndWait();

            return;
        }

        advisorComboBox.setItems(professors);
        registrantComboBox.setItems(professors);
        coAdvisorMenuButton.getItems().addAll(initializeCheckMenuItemsFromList(professors));

        title.textProperty().addListener((ov, oldValue, newValue) ->
        {
            if (title.getText().length() > MAX_NUMBER_CHARACTERS)
            {
                title.setText(title.getText().substring(0, MAX_NUMBER_CHARACTERS));
            }
        });
    }

    /**
     * Displays number of selected co-advisors.
     */
    @FXML
    public void onSelectCoAdvisors()
    {
        coAdvisorMenuButton.setText(getSelectedItemsNumber(coAdvisorMenuButton) + " selected");
    }

    /**
     * Displays number of selected authors.
     */
    @FXML
    public void onSelectAuthors()
    {
        authorMenuButton.setText(getSelectedItemsNumber(authorMenuButton) + " selected");
    }

    /**
     * Adds new POC.
     */
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
                            StandardCopyOption.REPLACE_EXISTING
                    );

                    List<Student> authors = new ArrayList<>();
                    UserDB userDB = new UserDB(connectDB.getConnection());

                    for (MenuItem item : authorMenuButton.getItems())
                    {
                        if (((CheckMenuItem) item).isSelected())
                        {
                            authors.add((Student) userDB.queryUserByID(item.getId()));
                        }
                    }

                    List<Professor> coAdvisors = new ArrayList<>();

                    for (MenuItem item : coAdvisorMenuButton.getItems())
                    {
                        if (((CheckMenuItem) item).isSelected())
                        {
                            coAdvisors.add((Professor) userDB.queryUserByID(item.getId()));
                        }
                    }

                    return new POCDB(connectDB.getConnection()).insertPOC(new POC(0,
                            title.getText().trim(),
                            authors,
                            datePicker.getValue(),
                            Arrays.stream(keywordsTextArea.getText().split(" ")).toList(),
                            summaryTextArea.getText().trim(),
                            fieldComboBox.getValue(),
                            new PDF(0, pdfFilepath, LocalDate.now()),
                            registrantComboBox.getValue(),
                            advisorComboBox.getValue(),
                            coAdvisors
                    ));
                }
            }
        };

        task.setOnSucceeded(workerStateEvent ->
                Main.loadNewSceneWithDataModel(mainPane, "search-poc-page-view.fxml", dataModel, "Search POC"));

        task.setOnFailed(workerStateEvent -> new Alert(Alert.AlertType.ERROR,
                "Couldn't insert POC: " + task.getException().getMessage(),
                ButtonType.OK).showAndWait());

        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        progressIndicator.managedProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        addPOCButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));

        new Thread(task).start();
    }

    /**
     * Opens up file manager to select PDF.
     */
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

    /**
     * Initializes a list of MenuItems from a list of Users.
     *
     * @param userList list containing the Users to be displayed on the MenuItems.
     * @return list containing initialized MenuItems from a list of Users.
     */
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

    /**
     * Get number of selected items in a MenuButton object.
     *
     * @param menuButton MenuButton object containing CheckMenuItems.
     * @return number of selected items.
     */
    private long getSelectedItemsNumber(MenuButton menuButton)
    {
        return menuButton.getItems()
                .stream()
                .filter(menuItem -> ((CheckMenuItem) menuItem).isSelected())
                .count();
    }

}