package com.ufv.project.controller;

import com.ufv.project.db.Singleton;
import com.ufv.project.db.UserDataSingleton;
import com.ufv.project.model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreatePOCController
{
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
        topMenuController.setUserIcon(new Image(new File("src/main/resources/com/ufv/project/images/anonymous_user.png").toURI().toString()));
        topMenuController.setUserRole("Teacher");

        // Sets values according to the current user.
        userDataController.setUsernameText(UserDataSingleton.getInstance().getUsername());
        userDataController.setEmailText(UserDataSingleton.getInstance().getEmail());
        userDataController.setNameText(UserDataSingleton.getInstance().getName());

        // Get data from db.
        ObservableList<Professor> professors = Singleton.getInstance().getProfessorList();
        authorMenuButton.getItems().setAll(initializeCheckMenuItemsFromList(Singleton.getInstance().getStudentList()));

        // Set data for choosing.
        advisorComboBox.setItems(professors);
        coAdvisorMenuButton.getItems().addAll(initializeCheckMenuItemsFromList(professors));
    }

    @FXML
    public void handlePOCAdding()
    {
        Singleton.getInstance().addPOC(new POC.POCBuilder()
                .title(title.getText())
                .defenseDate(datePicker.getValue())
                .advisor(new Professor("matt", "Matt", "kdka", "da", new ArrayList<>()))
                .coAdvisors(new ArrayList<>())
                .registrant(new Professor("matt", "Matt", "kdka", "da", new ArrayList<>()))
                .pdf(new PDF(1, pdfFile, LocalDate.now()))
                .field(new Field(1, "sa"))
                .summary("dadasdasdas")
                .keywords(new ArrayList<>())
                .build());

        // Testing things out.
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ufv/project/search-poc-page-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            ((Stage) gridPane.getScene().getWindow()).close();
        }
        catch (IOException e)
        {

        }
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