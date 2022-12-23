package com.example.cinemabookingsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

import static com.example.cinemabookingsystem.CinemaBookingSystem.moveTheScreen;
import static com.example.cinemabookingsystem.CinemaBookingSystem.removeTheDefaultAntetBar;


public class AutentificareController {
    @FXML
    private Button butonAutentificare;
    @FXML
    private AnchorPane formularAutentificare;
    @FXML
    private PasswordField parolaAutentificare;
    @FXML
    private TextField usernameAutentificare;
    public void closeAutentificare() {
        System.exit(0);
    }
    public void minimizeAutentificare() {
        Stage stage = (Stage) formularAutentificare.getScene().getWindow();
        stage.setIconified(true);

    }
    // tools for database
    private Connection connect;
    private PreparedStatement prepare;

    private Statement statement;
    private ResultSet result;

    public void autentificare() throws IOException {
        String sql = "SELECT * FROM UTILIZATOR WHERE USERNAME = ? AND PAROLA = ?";
        connect = DatabaseConnection.getConnection();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1,usernameAutentificare.getText());
            prepare.setString(2,parolaAutentificare.getText());
            result = prepare.executeQuery();
            Alert alert;
            //check is your username and password is empty
            if(usernameAutentificare.getText().isEmpty() || parolaAutentificare.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare autentificare");
                alert.setHeaderText(null);
                alert.setContentText("Vă rugam completati toate câmpurile");
                alert.showAndWait();
            }
            else {
                if(result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mesaj de informare");
                    alert.setContentText("Autentificare cu succes!");
                    alert.showAndWait();
                    butonAutentificare.getScene().getWindow().hide();
                    String username = (usernameAutentificare.getText());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MeniuUser.fxml"));
                    Parent root = loader.load();
                    MeniuUserController meniuUserController = loader.getController();
                    meniuUserController.displayUsername(username);
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    moveTheScreen(root, stage);
                    removeTheDefaultAntetBar(stage);

                    meniuUserController.getInapoi().setVisible(false);
                    meniuUserController.getDeconectare().setVisible(true);
                    meniuUserController.getButonContulMeu().setVisible(true);

                    stage.show();

                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare autentificare");
                    alert.setHeaderText(null);
                    alert.setContentText("Username sau parolă gresită");
                    alert.showAndWait();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
