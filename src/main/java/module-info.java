module com.example.djsoftware {
    requires javafx.controls;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires junit;

    opens com.example.djsoftware to javafx.fxml;
    exports com.example.djsoftware;
    exports com.example.djsoftware.tests;
    exports com.example.djsoftware.beans;
    exports com.example.djsoftware.controllers;
    opens com.example.djsoftware.controllers to javafx.fxml;
    exports com.example.djsoftware.components;
    opens com.example.djsoftware.components to javafx.fxml;
    opens com.example.djsoftware.beans to javafx.fxml;
}