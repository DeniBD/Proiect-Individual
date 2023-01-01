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
    private Statement statement;
    private ResultSet result;
    public void inregistrare(ActionEvent event) throws IOException, SQLException {

        String sql = "INSERT INTO UTILIZATOR (ID_UTILIZATOR, EMAIL,PAROLA,IS_ADMIN,USERNAME) VALUES (?,?,?,1,?)";
        String query = "select COUNT(ID_UTILIZATOR) from UTILIZATOR";
        connect = DatabaseConnection.getConnection();
        try (Statement stmt = connect.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int nr_utilizatori = rs.getInt("COUNT(ID_UTILIZATOR)");
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, nr_utilizatori + 1);
            prepare.setString(2, emailInregistrare.getText());
            prepare.setString(3, parolaInregistrare.getText());
            prepare.setString(4, usernameInregistrare.getText());
            Alert alert;
            if(usernameInregistrare.getText().isEmpty() || emailInregistrare.getText().isEmpty() || parolaInregistrare.getText().isEmpty()) {
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
