//package com.ufv.project.controller.java;
//
//import com.ufv.project.Main;
//import com.ufv.project.controller.java.UpdateUserController;
//import com.ufv.project.db.ConnectDB;
//import com.ufv.project.db.UserDB;
//import com.ufv.project.model.DataModel;
//import com.ufv.project.model.User;
//
//import java.sql.SQLException;
//import java.util.List;
//
//public class SearchUserController
//{
//
//    private final DataModel dataModel;
//
//    public SearchUserController(DataModel dataModel)
//    {
//        this.dataModel = dataModel;
//    }
//
//    @FXML
//    public void initialize()
//    {
//
//    }
//
//    @FXML
//    public void onSearchUserButtonClicked()
//    {
//        final Task<List<User>> task = new Task<>()
//        {
//            @Override
//            protected List<User> call() throws SQLException
//            {
//                try (ConnectDB connectDB = new ConnectDB())
//                {
//                    UserDB userDB = new UserDB(connectDB.getConnection());
//                    List<User> users = userDB.queryUsersByContainsID(searchUsernameTextField.getText().trim());
//
//                    if (users == null || users.isEmpty())
//                    {
//                        return userDB.queryUsers();
//                    }
//
//                    return users;
//                }
//            }
//        };
//
//        task.setOnSucceeded(workerStateEvent ->
//        {
//            userList.setManaged(true);
//            userList.setVisible(true);
//            userList.getItems().setAll(task.getValue());
//        });
//
//        task.setOnFailed(workerStateEvent -> task.getException().printStackTrace());
//
//        new Thread(task).start();
//        progressIndicator.progressProperty().bind(task.progressProperty());
//        progressIndicator.managedProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
//        progressIndicator.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
//    }
//
//    @FXML
//    public void onSelectedRow()
//    {
//        User user = userList.getSelectionModel().getSelectedItem();
//
//        if (user == null)
//        {
//            return;
//        }
//
//        ((UpdateUserController) Main.loadStageWithDataModel("update-user-page-view.fxml", dataModel, "Update User")).setData(user);
//    }
//
//}
