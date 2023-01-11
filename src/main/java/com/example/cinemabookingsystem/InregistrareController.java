package com.example.cinemabookingsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

import static com.example.cinemabookingsystem.CinemaBookingSystem.moveTheScreen;
import static com.example.cinemabookingsystem.CinemaBookingSystem.removeTheDefaultAntetBar;

public class InregistrareController {
    @FXML
    private Button butonInregistrare;
    @FXML
    private TextField emailInregistrare;
    @FXML
    private AnchorPane formularInregistrare;
    @FXML
    private PasswordField parolaInregistrare;
    @FXML
    private TextField usernameInregistrare;
    @FXML
    private TextField nrTelInregistrare;
    @FXML
    private TextField numeInregistrare;
    @FXML
    private TextField prenumeInregistrare;
    public void closeInregistrare() {
        System.exit(0);
    }
    public void minimizeInregistrare() {
        Stage stage = (Stage) formularInregistrare.getScene().getWindow();
        stage.setIconified(true);
    }
    // tools for database
    private Connection connect;
    private PreparedStatement prepare;
    public void inregistrare(ActionEvent event) throws IOException, SQLException {

        String sql = "INSERT INTO UTILIZATOR (NUME, PRENUME, EMAIL,PAROLA,IS_ADMIN,USERNAME, NR_TEL) VALUES (?,?,?,?,0,?,?)";
        connect = DatabaseConnection.getConnection();
        try (Statement stmt = connect.createStatement()) {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, numeInregistrare.getText());
            prepare.setString(2, prenumeInregistrare.getText());
            prepare.setString(3, emailInregistrare.getText());
            prepare.setString(4, parolaInregistrare.getText());
            prepare.setString(5, usernameInregistrare.getText());
            prepare.setString(6, nrTelInregistrare.getText());

            Alert alert;
            if(usernameInregistrare.getText().isEmpty() || emailInregistrare.getText().isEmpty() || parolaInregistrare.getText().isEmpty() || numeInregistrare.getText().isEmpty() || prenumeInregistrare.getText().isEmpty() || nrTelInregistrare.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare înregistrare");
                alert.setHeaderText(null);
                alert.setContentText("Vă rugam completati toate câmpurile");
                alert.showAndWait();
            } else if(parolaInregistrare.getText().length() < 8) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare înregistrare");
                alert.setHeaderText(null);
                alert.setContentText("Parolă invalidă");
                alert.showAndWait();

            } else if (!emailInregistrare.getText().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-z]+$")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare înregistrare");
                alert.setHeaderText(null);
                alert.setContentText("Email invalid");
                alert.showAndWait();
            } else if (nrTelInregistrare.getText().length() != 10) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare înregistrare");
                alert.setHeaderText(null);
                alert.setContentText("Număr de telefon invalid");
                alert.showAndWait();
            } else {
                prepare.execute();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mesaj de informare");
                alert.setContentText("Înregistrare cu succes!");
                alert.showAndWait();
                usernameInregistrare.setText("");
                parolaInregistrare.setText("");
                emailInregistrare.setText("");
                if (event.getSource() == butonInregistrare) {
                    butonInregistrare.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Index.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    moveTheScreen(root, stage);
                    removeTheDefaultAntetBar(stage);
                    stage.show();
                }
            }

        }catch (Exception e) {
            e.printStackTrace();

        }

    }


}
