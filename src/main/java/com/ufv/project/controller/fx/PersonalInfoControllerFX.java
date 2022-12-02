package com.ufv.project.controller.fx;

import com.ufv.project.db.ConnectDB;
import com.ufv.project.db.Professor_has_subjectDB;
import com.ufv.project.model.DataModel;
import com.ufv.project.model.Subject;
import com.ufv.project.model.UserTypesEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class PersonalInfoControllerFX
{
    @FXML
    private Text usernameText;

    @FXML
    private Text userTypeText;

    @FXML
    private Text nameText;

    @FXML
    private Label emailLabel;

    @FXML
    private Text emailText;

    @FXML
    private Label registrationLabel;

    @FXML
    private Text registrationText;

    @FXML
    private Label POCIDLabel;

    @FXML
    private Text POCIDText;

    @FXML
    private Label professorSubjectsLabel;

    @FXML
    private ListView<Subject> professorSubjectListView;

    private final DataModel dataModel;

    /**
     * Constructor for PersonalInfoControllerFX.
     */
    public PersonalInfoControllerFX(DataModel dataModel)
    {
        this.dataModel = dataModel;
    }

    /**
     * Runs upon initialization.
     */
    @FXML
    public void initialize()
    {
        UserTypesEnum userType = dataModel.getUserType();

        usernameText.setText(dataModel.getUsername());
        nameText.setText(dataModel.getName());
        userTypeText.setText(userType.toString());

        if (userType == UserTypesEnum.STUDENT)
        {
            POCIDLabel.setManaged(true);
            POCIDLabel.setManaged(true);
            POCIDText.setVisible(true);
            POCIDText.setVisible(true);

            professorSubjectsLabel.setManaged(false);
            professorSubjectListView.setManaged(false);
            professorSubjectsLabel.setVisible(false);
            professorSubjectListView.setVisible(false);

            registrationLabel.setManaged(true);
            registrationText.setManaged(true);
            registrationLabel.setVisible(true);
            registrationText.setVisible(true);

            emailLabel.setManaged(true);
            emailText.setManaged(true);
            emailLabel.setVisible(true);
            emailText.setVisible(true);

            emailText.setText(dataModel.getEmail());
            registrationText.setText(dataModel.getRegistration());
            POCIDText.setText(String.valueOf(dataModel.getPOCID()));
        }
        else if (userType == UserTypesEnum.PROFESSOR)
        {
            POCIDLabel.setManaged(false);
            POCIDLabel.setManaged(false);
            POCIDText.setVisible(false);
            POCIDText.setVisible(false);

            registrationLabel.setManaged(false);
            registrationText.setManaged(false);
            registrationLabel.setVisible(false);
            registrationText.setVisible(false);

            emailLabel.setManaged(true);
            emailText.setManaged(true);
            emailLabel.setVisible(true);
            emailText.setVisible(true);

            professorSubjectsLabel.setManaged(true);
            professorSubjectListView.setManaged(true);
            professorSubjectsLabel.setVisible(true);
            professorSubjectListView.setVisible(true);

            emailText.setText(dataModel.getEmail());

            try (ConnectDB connectDB = new ConnectDB())
            {
                professorSubjectListView
                        .getItems()
                        .setAll(new Professor_has_subjectDB(connectDB.getConnection())
                                .querySubjectsByProfessor(dataModel.getUsername()));
            }
            catch (SQLException e)
            {
                new Alert(Alert.AlertType.ERROR,
                        "Couldn't get subjects for professor: " + e.getMessage(),
                        ButtonType.OK);
            }
        }
        else if (userType == UserTypesEnum.ADMIN)
        {
            POCIDLabel.setManaged(false);
            POCIDLabel.setManaged(false);
            POCIDText.setVisible(false);
            POCIDText.setVisible(false);

            professorSubjectsLabel.setManaged(false);
            professorSubjectListView.setManaged(false);
            professorSubjectsLabel.setVisible(false);
            professorSubjectListView.setVisible(false);

            registrationLabel.setManaged(false);
            registrationText.setManaged(false);
            registrationLabel.setVisible(false);
            registrationText.setVisible(false);

            emailLabel.setManaged(false);
            emailText.setManaged(false);
            emailLabel.setVisible(false);
            emailText.setVisible(false);
        }
    }

}