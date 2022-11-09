package com.ufv.project.controller;

import com.ufv.project.Main;
import com.ufv.project.db.Singleton;
import com.ufv.project.model.POC;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Stack;
import java.util.function.Function;

public class SearchPOCController
{
    // ----------- Layout -----------
    @FXML
    private VBox vBox;
    // ------------------------------

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

        pocListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<POC>()
        {
            @Override
            public void changed(ObservableValue<? extends POC> observableValue, POC poc, POC t1)
            {
                ((AnalyzePOCController) Main.loadStage("analyze-poc-page-view.fxml")).setData(pocListView.getSelectionModel().getSelectedItem());
                Main.closeCurrentStage(vBox);
            }
        });
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