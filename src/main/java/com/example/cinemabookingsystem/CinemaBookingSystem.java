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
    private static double x = 0;
    private static double y = 0;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Index.fxml")));
        stage.setScene(new Scene(root));
        moveTheScreen(root, stage);
        removeTheDefaultAntetBar(stage);
        stage.show();
    }
    public static void moveTheScreen (Parent root, Stage stage) {
        root.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }
    public static void removeTheDefaultAntetBar (Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);
    }
    public static void main(String[] args) {
        launch();
    }
}
