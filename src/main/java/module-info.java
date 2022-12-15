module com.example.cinemabookingsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cinemabookingsystem to javafx.fxml;
    exports com.example.cinemabookingsystem;
}