//package com.ufv.project.controller.java;
//
//import com.ufv.project.Main;
//import com.ufv.project.controller.java.AnalyzePOCController;
//import com.ufv.project.controller.java.TopMenuControllerFX;
//import com.ufv.project.db.ConnectDB;
//import com.ufv.project.db.POCDB;
//import com.ufv.project.db.POCSearchTypesEnum;
//import com.ufv.project.model.DataModel;
//import com.ufv.project.model.POC;
//
//import java.io.File;
//import java.sql.SQLException;
//import java.util.HashSet;
//
//public class SearchPOCController
//{
//
//
//    private final DataModel dataModel;
//
//    public SearchPOCController(DataModel dataModel)
//    {
//        this.dataModel = dataModel;
//    }
//
//    @FXML
//    public void initialize()
//    {
//        // Set event handling to text box when pressing the "ENTER" key.
//        searchPOCTextField.setOnKeyPressed(keyEvent ->
//        {
//            if (keyEvent.getCode().equals(KeyCode.ENTER) && !searchPOCTextField.getText().trim().isEmpty())
//            {
//                handlePOCSearching();
//            }
//        });
//
//        // Take away focus from the box.
//        searchPOCTextField.setFocusTraversable(false);
//
//        // Hide list while user has not searched for the poc.
//        pocListView.setVisible(false);
//
//        pocListView.getSelectionModel().selectedItemProperty().addListener((observableValue, poc, t1) ->
//        {
//            ((AnalyzePOCController) Main.loadStageWithDataModel("analyze-poc-page-view.fxml", dataModel, "Analyze POC"))
//                    .setData(pocListView.getSelectionModel().getSelectedItem());
//        });
//    }
//
//    @FXML
//    public void handlePOCSearching()
//    {
//        // Load items from database.
//        ObservableList<POC> pocList = null;
//        HashSet<POCSearchTypesEnum> pocSearchTypes = getSearchTypes();
//
//        try (ConnectDB connectDB = new ConnectDB())
//        {
//            pocList = FXCollections.observableList(new POCDB(connectDB.getConnection()).queryPOCsByType(pocSearchTypes,
//                    searchPOCTextField.getText().trim()));
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//
//        if (pocList == null || pocList.isEmpty())
//        {
//            File file = new File("src/main/resources/com/ufv/project/images/anonymous_user.png");
//
//            noPOCImage.setImage(new Image(file.toURI().toString()));
//            return;
//        }
//
//        // Transfer items to list in the view.
//        pocListView.setItems(pocList);
//
//        // Shows up list with the results.
//        pocListView.setVisible(true);
//    }
//
//    private HashSet<POCSearchTypesEnum> getSearchTypes()
//    {
//        HashSet<POCSearchTypesEnum> searchTypes = new HashSet<>();
//
//        if (searchByTitleCheckBox.isSelected())
//        {
//            searchTypes.add(POCSearchTypesEnum.TITLE);
//        }
//
//        if (searchBySummaryCheckBox.isSelected())
//        {
//            searchTypes.add(POCSearchTypesEnum.SUMMARY);
//        }
//
//        if (searchByAuthorCheckBox.isSelected())
//        {
//            searchTypes.add(POCSearchTypesEnum.AUTHOR);
//        }
//
//        if (searchByAdvisorCheckBox.isSelected())
//        {
//            searchTypes.add(POCSearchTypesEnum.ADVISOR);
//        }
//
//        if (searchByFieldCheckBox.isSelected())
//        {
//            searchTypes.add(POCSearchTypesEnum.FIELD);
//        }
//
//        return searchTypes;
//    }
//
//}