package com.ufv.project.controller;

import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.Professor_has_subjectDB;
import com.ufv.project.model.DataModel;
import com.ufv.project.model.Subject;
import com.ufv.project.model.UserTypesEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class AnalyzeUserController
{
    @FXML
    private VBox mainPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label registrationLabel;

    @FXML
    private TextField registrationTextField;

    @FXML
    private Label professorSubjectsLabel;

    @FXML
    private ListView<Subject> professorSubjectListView;

    private final DataModel dataModel;

    public AnalyzeUserController(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    @FXML
    public void initialize()
    {
        usernameTextField.setText(dataModel.getUsername());
        nameTextField.setText(dataModel.getName());
        UserTypesEnum userType = dataModel.getUserType();

        if (userType == UserTypesEnum.STUDENT)
        {
            professorSubjectsLabel.setManaged(false);
            professorSubjectListView.setManaged(false);
            professorSubjectsLabel.setVisible(false);
            professorSubjectListView.setVisible(false);

            registrationLabel.setManaged(true);
            registrationTextField.setManaged(true);
            registrationLabel.setVisible(true);
            registrationTextField.setVisible(true);

            emailLabel.setManaged(true);
            emailTextField.setManaged(true);
            emailLabel.setVisible(true);
            emailTextField.setVisible(true);

            registrationTextField.setText(dataModel.getRegistration());
            emailTextField.setText(dataModel.getEmail());
        }
        else if (userType == UserTypesEnum.PROFESSOR)
        {
            registrationLabel.setManaged(false);
            registrationTextField.setManaged(false);
            registrationLabel.setVisible(false);
            registrationTextField.setVisible(false);

            emailLabel.setManaged(true);
            emailTextField.setManaged(true);
            emailLabel.setVisible(true);
            emailTextField.setVisible(true);

            professorSubjectsLabel.setManaged(true);
            professorSubjectListView.setManaged(true);
            professorSubjectsLabel.setVisible(true);
            professorSubjectListView.setVisible(true);

            emailTextField.setText(dataModel.getEmail());

            try (ConnectDB connectDB = new ConnectDB())
            {
                professorSubjectListView
                        .getItems()
                        .setAll(new Professor_has_subjectDB(connectDB.getConnection())
                                .querySubjectsByProfessor(dataModel.getUsername()));
            }
            catch (SQLException e)
            {
                System.out.println("ERROR: Couldn't get subjects for professor: " + e.getMessage());
            }
        }
        else if (userType == UserTypesEnum.ADMIN)
        {
            professorSubjectsLabel.setManaged(false);
            professorSubjectListView.setManaged(false);
            professorSubjectsLabel.setVisible(false);
            professorSubjectListView.setVisible(false);

            registrationLabel.setManaged(false);
            registrationTextField.setManaged(false);
            registrationLabel.setVisible(false);
            registrationTextField.setVisible(false);

            emailLabel.setManaged(false);
            emailTextField.setManaged(false);
            emailLabel.setVisible(false);
            emailTextField.setVisible(false);
        }
    }

}
