package com.ufv.project.controller.java;
//
//import com.ufv.project.Main;
//import com.ufv.project.controller.java.PersonalInfoController;
//import com.ufv.project.db.ConnectDB;
//import com.ufv.project.db.SubjectDB;
//import com.ufv.project.db.UserDB;
//import com.ufv.project.model.*;
//
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
public class UpdateUserController
{

    //    private final DataModel dataModel;
//
//    public UpdateUserController(DataModel dataModel)
//    {
//        this.dataModel = dataModel;
//    }
//
//
//    public void setData(User user)
//    {
//        usernameTextField.setText(user.getUsername());
//        nameTextField.setText(user.getName());
//        UserTypesEnum userType = user.getUserType();
//
//        if (userType == UserTypesEnum.ADMIN) {
//            POCIDLabel.setManaged(false);
//            POCIDTextField.setManaged(false);
//            POCIDLabel.setVisible(false);
//            POCIDTextField.setVisible(false);
//
//            professorSubjectsLabel.setManaged(false);
//            professorSubjects.setManaged(false);
//            professorSubjectsLabel.setVisible(false);
//            professorSubjects.setVisible(false);
//
//            registrationLabel.setManaged(false);
//            registrationTextField.setManaged(false);
//            registrationLabel.setVisible(false);
//            registrationTextField.setVisible(false);
//
//            emailLabel.setManaged(false);
//            emailTextField.setManaged(false);
//            emailLabel.setVisible(false);
//            emailTextField.setVisible(false);
//        }
//        else if (userType == UserTypesEnum.STUDENT)
//        {
//            POCIDLabel.setManaged(true);
//            POCIDTextField.setManaged(true);
//            POCIDLabel.setVisible(true);
//            POCIDTextField.setVisible(true);
//
//            professorSubjectsLabel.setManaged(false);
//            professorSubjects.setManaged(false);
//            professorSubjectsLabel.setVisible(false);
//            professorSubjects.setVisible(false);
//
//            registrationLabel.setManaged(true);
//            registrationTextField.setManaged(true);
//            registrationLabel.setVisible(true);
//            registrationTextField.setVisible(true);
//
//            emailLabel.setManaged(true);
//            emailTextField.setManaged(true);
//            emailLabel.setVisible(true);
//            emailTextField.setVisible(true);
//        }
//        else if (userType == UserTypesEnum.PROFESSOR)
//        {
//            POCIDLabel.setManaged(false);
//            POCIDTextField.setManaged(false);
//            POCIDLabel.setVisible(false);
//            POCIDTextField.setVisible(false);
//
//            registrationLabel.setManaged(false);
//            registrationTextField.setManaged(false);
//            registrationLabel.setVisible(false);
//            registrationTextField.setVisible(false);
//
//            emailLabel.setManaged(true);
//            emailTextField.setManaged(true);
//            emailLabel.setVisible(true);
//            emailTextField.setVisible(true);
//
//            professorSubjectsLabel.setManaged(true);
//            professorSubjects.setManaged(true);
//            professorSubjectsLabel.setVisible(true);
//            professorSubjects.setVisible(true);
//        }
//
//        final Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws SQLException {
//                try (ConnectDB connectDB = new ConnectDB())
//                {
//                    if (userType == UserTypesEnum.STUDENT)
//                    {
//                        Student student = (Student) user;
//
//                        registrationTextField.setText(student.getRegistration());
//                        emailTextField.setText(student.getEmail());
//                        POCIDTextField.setText(String.valueOf(student.getPOCID()));
//                    }
//                    else if (userType == UserTypesEnum.PROFESSOR)
//                    {
//                        Professor professor = (Professor) user;
//
//                        emailTextField.setText(professor.getEmail());
//
//                        List<Subject> markedSubjects = professor.getSubjectsTaught();
//
//                        professorSubjects.getItems().setAll(initializeCheckMenuItemsFromList(new SubjectDB(connectDB.getConnection()).querySubjects()));
//
//                        for (MenuItem menuItem : professorSubjects.getItems())
//                        {
//                            markedSubjects.stream().map(Subject::getId).forEach(integer -> ((CheckMenuItem) menuItem)
//                                    .setSelected(integer == Integer.parseInt(menuItem.getId())));
//                        }
//                    }
//
//                    return null;
//                }
//            }
//        };
//
//        task.setOnSucceeded(workerStateEvent -> onSelectSubject());
//
//        new Thread(task).start();
//    }
//
//    @FXML
//    public void onUpdateButtonClicked()
//    {
//        if (!arePasswordsEqual())
//        {
//            // Display error style on password input boxes.
//            return;
//        }
//
//        final Task<User> task = new Task<>() {
//            @Override
//            protected User call() throws Exception {
//                User user = null;
//
//                if (dataModel.getUserType() == UserTypesEnum.STUDENT) {
//                    String POCIDText = POCIDTextField.getText().trim();
//                    int POCID = 0;
//
//                    if (!POCIDText.isEmpty()) {
//                        POCID = Integer.parseInt(POCIDText);
//                    }
//
//                    user = new Student(usernameTextField.getText().trim(),
//                            nameTextField.getText().trim(),
//                            passwordField.getText(),
//                            registrationTextField.getText().trim(),
//                            POCID,
//                            emailTextField.getText().trim());
//                } else if (dataModel.getUserType() == UserTypesEnum.PROFESSOR) {
//                    List<Integer> subjectIDList = professorSubjects
//                            .getItems()
//                            .stream()
//                            .filter(menuItem -> ((CheckMenuItem) menuItem).isSelected())
//                            .map(menuItem -> Integer.parseInt(menuItem.getId()))
//                            .toList();
//
//                    try (ConnectDB connectDB = new ConnectDB()) {
//                        SubjectDB subjectDB = new SubjectDB(connectDB.getConnection());
//                        List<Subject> subjectList = new ArrayList<>();
//
//                        for (int id : subjectIDList) {
//                            subjectList.add(subjectDB.querySubjectByID(id));
//                        }
//
//                        user = new Professor(usernameTextField.getText().trim(),
//                                nameTextField.getText().trim(),
//                                passwordField.getText(),
//                                emailTextField.getText().trim(),
//                                subjectList);
//                    }
//                } else if (dataModel.getUserType() == UserTypesEnum.ADMIN) {
//                    user = new Administrator(usernameTextField.getText().trim(),
//                            nameTextField.getText().trim(),
//                            passwordField.getText());
//                }
//
//                if (user == null) {
//                    throw new IllegalAccessException("ERROR: No role was assigned.");
//                }
//
//                try (ConnectDB connectDB = new ConnectDB()) {
//                    return new UserDB(connectDB.getConnection()).updateUser(user);
//                }
//            }
//        };
//
//        task.setOnSucceeded(workerStateEvent -> Main.closeCurrentStage(mainPane));
//
//        task.setOnFailed(workerStateEvent -> task.getException().printStackTrace());
//
//        new Thread(task).start();
//        progressIndicator.progressProperty().bind(task.progressProperty());
//        progressIndicator.visibleProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
//        updateUserButton.disableProperty().bind(Bindings.when(task.runningProperty()).then(true).otherwise(false));
//    }
//
//    @FXML
//    public void onSelectSubject()
//    {
//        professorSubjects.setText(professorSubjects.getItems().stream()
//                .filter(menuItem -> ((CheckMenuItem) menuItem).isSelected())
//                .count() + " subject(s) selected");
//    }
//
//    public boolean arePasswordsEqual() {
//        return passwordField.getText().equals(confirmPasswordField.getText());
//    }
//
//    public List<MenuItem> initializeCheckMenuItemsFromList(List<Subject> subjectList) {
//        List<MenuItem> items = new ArrayList<>();
//
//        for (Subject subject : subjectList) {
//            CheckMenuItem menuItem = new CheckMenuItem();
//
//            menuItem.setId(String.valueOf(subject.getId()));
//            menuItem.setText(subject.getName() + " - " + subject.getDescription());
//
//            items.add(menuItem);
//        }
//
//        return items;
//    }
//
    public static boolean arePasswordsEqual(String password, String secondPassword)
    {
        return password.equals(secondPassword);
    }

}
