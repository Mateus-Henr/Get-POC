module com.ufv.project
{
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ufv.project to javafx.fxml;
    exports com.ufv.project;
    exports com.ufv.project.controller;
    opens com.ufv.project.controller to javafx.fxml;
}