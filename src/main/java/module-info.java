module com.example.cinemabookingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.cinemabookingsystem to javafx.fxml;
    exports com.example.cinemabookingsystem;
}