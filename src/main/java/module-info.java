module com.example.cinemabookingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires javax.mail;


    opens com.example.cinemabookingsystem to javafx.fxml;
    exports com.example.cinemabookingsystem;
    exports com.example.cinemabookingsystem.classes;
    opens com.example.cinemabookingsystem.classes to javafx.fxml;
}