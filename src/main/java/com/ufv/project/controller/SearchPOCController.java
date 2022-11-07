package com.ufv.project.controller;

import com.ufv.project.db.Singleton;
import com.ufv.project.model.POC;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.io.File;

public class SearchPOCController
{
    // ---------- Top Menu ----------
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
    }

    @FXML
    public void handlePOCSearching()
    {
        // Load items from database.
        ObservableList<POC> pocList = Singleton.getInstance().getPocList();

        if (pocList.isEmpty())
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