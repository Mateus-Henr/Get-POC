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

public class UpdatePOCController
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
    private TopMenuController topMenuControllerFX;
    // ------------------------------

    // ---- Personal information ----
    @FXML
    private VBox userData;

    @FXML
    private PersonalInfoControllerFX userDataController;
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
    private ComboBox<Professor> registrantComboBox;

    @FXML
    private TextArea keywordsTextArea;

    @FXML
    private Text pdfFilepathText;

    @FXML
    private Button updatePOCButton;

    @FXML
    private ProgressIndicator progressIndicator;
    // ------------------------------

    private final DataModel dataModel;

    private POC poc;

    private File pdfFile;

    public static final String PDF_STORAGE_FOLDER = "src" + File.separator +
            "main" + File.separator +
            "resources" + File.separator +
            "com" + File.separator +
            "ufv" + File.separator +
            "project" + File.separator + "pdfs" + File.separator;

    public UpdatePOCController(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize()
    {

    }

    @FXML
    public void onSelectCoAdvisors()
    {
        coAdvisorMenuButton.setText(getSelectedItemsNumber(coAdvisorMenuButton) + " co-advisor(s) selected");
    }

    @FXML
    public void onSelectAuthors()
    {
        coAdvisorMenuButton.setText(getSelectedItemsNumber(coAdvisorMenuButton) + " author(s) selected");
    }

    @FXML
    public void onUpdateButtonClicked()
    {
        final Task<POC> task = new Task<>()
        {
            @Override
            protected POC call() throws SQLException, IOException
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

                    return new POCDB(connectDB.getConnection()).updatePOC(new POC(poc.getId(),
                            title.getText().trim(),
                            authors,
                            datePicker.getValue(),
                            Arrays.stream(keywordsTextArea.getText().split(" ")).toList(),
                            summaryTextArea.getText().trim(),
                            fieldComboBox.getValue(),
                            new PDF(poc.getPdf().getId(), pdfFilepath, LocalDate.now()),
                            registrantComboBox.getValue(),
                            advisorComboBox.getValue(),
                            coAdvisors
                    ));
                }
            }
        };

        task.setOnSucceeded(workerStateEvent ->
        {
            Main.loadStageWithDataModel("search-poc-page-view.fxml", dataModel, "Search POC");
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
        progressIndicator.managedProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        updatePOCButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
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

    public void setPOCData(POC poc)
    {
        this.poc = poc;

        title.setText(poc.getTitle());
        datePicker.setValue(poc.getDefenseDate());
        summaryTextArea.setText(poc.getSummary());
        keywordsTextArea.setText(String.join(" ", poc.getKeywords()));
        pdfFile = new File(poc.getPdf().getPath());
        pdfFilepathText.setText(poc.getPdf().getPath());

        try (ConnectDB connectDB = new ConnectDB())
        {
            authorMenuButton.getItems().setAll(initializeCheckMenuItemsFromList(new StudentDB(connectDB.getConnection()).querStudents()));
            checkMenuItemUser(authorMenuButton.getItems(), poc.getAuthors());

            ObservableList<Professor> professors = FXCollections.observableList(new ProfessorDB(connectDB.getConnection()).queryProfessors());

            coAdvisorMenuButton.getItems().setAll(initializeCheckMenuItemsFromList(professors));
            checkMenuItemUser(coAdvisorMenuButton.getItems(), poc.getCoAdvisors());

            advisorComboBox.setItems(professors);
            advisorComboBox.getSelectionModel().select(findIndex(advisorComboBox.getItems(), poc.getAdvisor()));

            fieldComboBox.setItems(FXCollections.observableList(new FieldDB(connectDB.getConnection()).queryFields()));
            fieldComboBox.getSelectionModel().select(findIndex(fieldComboBox.getItems(), poc.getField()));

            registrantComboBox.setItems(professors);
            registrantComboBox.getSelectionModel().select(findIndex(registrantComboBox.getItems(), poc.getRegistrant()));
        }
        catch (SQLException e)
        {
            System.out.println("Couldn't get data to update POC: " + e.getMessage());
        }
    }

    private int findIndex(List<? extends User> userList, User user)
    {
        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).getUsername().equals(user.getUsername()))
            {
                return i;
            }
        }

        return -1;
    }

    private int findIndex(List<Field> userList, Field field)
    {
        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).getId() == field.getId())
            {
                return i;
            }
        }

        return -1;
    }

    private void checkMenuItemUser(List<MenuItem> allItems, List<? extends User> userList)
    {
        for (MenuItem menuItem : allItems)
        {
            userList.stream().map(User::getUsername)
                    .forEach(s -> ((CheckMenuItem) menuItem).setSelected(s.equals(menuItem.getId()) || ((CheckMenuItem) menuItem).isSelected()));
        }
    }

    private long getSelectedItemsNumber(MenuButton menuButton)
    {
        return menuButton.getItems()
                .stream()
                .filter(menuItem -> ((CheckMenuItem) menuItem).isSelected())
                .count();
    }

}
