package com.example.cinemabookingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

import static com.example.cinemabookingsystem.DatabaseConnection.getConnection;

public class CinemaBookingSystem extends Application {
    private double x = 0;
    private double y = 0;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Formulare.fxml"));

        getConnection();

        //move the screen
        root.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
        //remove the head bar
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root, 1150, 650));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}