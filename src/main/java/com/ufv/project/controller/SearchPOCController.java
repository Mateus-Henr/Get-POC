package com.ufv.project.controller;

import com.ufv.project.Main;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.POCDB;
import com.ufv.project.model.POC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.sql.SQLException;

public class SearchPOCController
{
    // ----------- Layout -----------
    @FXML
    private VBox mainPane;
    // ------------------------------

    // ---------- Top Menu ----------
    @FXML
    private BorderPane topMenu;

    @FXML
    private TopMenuController topMenuController;
    // ------------------------------

    // --------- Search POC ---------
    @FXML
    private TextField searchPOCTextField;

    @FXML
    private ImageView noPOCImage;

    @FXML
    private ListView<POC> pocListView;

    @FXML
    private ProgressIndicator progressIndicator;
    // ------------------------------

    @FXML
    public void initialize()
    {
        // Set event handling to text box when pressing the "ENTER" key.
        searchPOCTextField.setOnKeyPressed(keyEvent ->
        {
            if (keyEvent.getCode().equals(KeyCode.ENTER) && !searchPOCTextField.getText().trim().isEmpty())
            {
                handlePOCSearching();
            }
        });

        // Take away focus from the box.
        searchPOCTextField.setFocusTraversable(false);

        // Hide list while user has not searched for the poc.
        pocListView.setVisible(false);

        pocListView.getSelectionModel().selectedItemProperty().addListener((observableValue, poc, t1) ->
        {
            ((AnalyzePOCController) Main.loadStage("analyze-poc-page-view.fxml", "Analyze POC")).setData(pocListView.getSelectionModel().getSelectedItem());
            Main.closeCurrentStage(mainPane);
        });
    }

    @FXML
    public void handlePOCSearching()
    {
        // Load items from database.
        ObservableList<POC> pocList = null;

        try (ConnectDB connectDB = new ConnectDB())
        {
            pocList = FXCollections.observableList(new POCDB(connectDB.getConnection()).queryAllPOCs());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (pocList == null || pocList.isEmpty())
        {
            File file = new File("src/main/resources/com/ufv/project/images/anonymous_user.png");

            noPOCImage.setImage(new Image(file.toURI().toString()));
            return;
        }

        // Transfer items to list in the view.
        pocListView.setItems(pocList);

        // Shows up list with the results.
        pocListView.setVisible(true);
    }

}