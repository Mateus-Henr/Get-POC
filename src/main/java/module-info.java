module com.ufv.project
{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires junit;
    requires java.desktop;
    requires ibatis.core;

    opens com.ufv.project to javafx.fxml;
    opens com.ufv.project.model to javafx.fxml;
    exports com.ufv.project;
    exports com.ufv.project.controller;
    exports com.ufv.project.model;
    opens com.ufv.project.controller to javafx.fxml;
    exports com.ufv.project.db;
    opens com.ufv.project.db to javafx.fxml;
}