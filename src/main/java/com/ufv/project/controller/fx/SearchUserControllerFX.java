package com.ufv.project.controller.fx;

import com.ufv.project.Main;
import com.ufv.project.controller.java.SearchUserController;
import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.UserDB;
import com.ufv.project.model.User;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class SearchUserControllerFX
{
    @FXML
    private VBox mainPane;

    @FXML
    private TextField searchUsernameTextField;

    @FXML
    private ListView<User> userList;

    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * Searches for User with given id.
     */
    @FXML
    public void onSearchUserButtonClicked()
    {
        final Task<List<User>> task = new Task<>()
        {
            @Override
            protected List<User> call() throws SQLException
            {
                try (ConnectDB connectDB = new ConnectDB())
                {
                    UserDB userDB = new UserDB(connectDB.getConnection());
                    List<User> users = userDB.queryUsersByContainsID(searchUsernameTextField.getText().trim());

                    if (users == null || users.isEmpty())
                    {
                        return userDB.queryUsers();
                    }

                    return users;
                }
            }
        };

        task.setOnSucceeded(workerStateEvent -> userList.getItems().setAll(task.getValue()));

        task.setOnFailed(workerStateEvent -> new Alert(Alert.AlertType.ERROR,
                "Couldn't get user(s): " + task.getException().getMessage(),
                ButtonType.OK));

        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.managedProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
        progressIndicator.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));

        new Thread(task).start();
    }

    /**
     * Opens up update page to update User.
     */
    @FXML
    public void onSelectedRow()
    {
        User user = userList.getSelectionModel().getSelectedItem();

        if (user == null)
        {
            return;
        }

        Main.loadNewSceneWithDataModel(mainPane, "update-user-page-view.fxml", SearchUserController.setDataModelFromUserType(user), "Update User");
    }

}
