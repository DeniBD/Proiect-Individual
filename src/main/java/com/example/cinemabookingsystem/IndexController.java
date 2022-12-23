package com.example.cinemabookingsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.example.cinemabookingsystem.CinemaBookingSystem.moveTheScreen;
import static com.example.cinemabookingsystem.CinemaBookingSystem.removeTheDefaultAntetBar;

public class IndexController {
    @FXML
    private Button butonAutentificareIndex;
    @FXML
    private Button butonInregistrareIndex;
    @FXML
    private AnchorPane index;
    public void closeIndex() {
        System.exit(0);
    }
    public void minimizeIndex() {
        Stage stage = (Stage) index.getScene().getWindow();
        stage.setIconified(true);
    }
    public void switchIndex(ActionEvent event) throws IOException {
        if (event.getSource() == butonInregistrareIndex) {
            butonInregistrareIndex.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Inregistrare.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            moveTheScreen(root, stage);
            removeTheDefaultAntetBar(stage);
            stage.show();
        } else if (event.getSource() == butonAutentificareIndex) {
            butonInregistrareIndex.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Autentificare.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            moveTheScreen(root, stage);
            removeTheDefaultAntetBar(stage);
            stage.show();
        }
        else {
            butonInregistrareIndex.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MeniuUser.fxml"));
            Parent root = loader.load();
            MeniuUserController meniuUserController = loader.getController();
            meniuUserController.getDeconectare().setVisible(false);
            meniuUserController.getInapoi().setVisible(true);
            meniuUserController.getButonContulMeu().setVisible(false);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            moveTheScreen(root, stage);
            removeTheDefaultAntetBar(stage);

            stage.show();
        }

    }

}
