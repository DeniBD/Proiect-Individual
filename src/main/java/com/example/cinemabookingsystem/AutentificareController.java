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
import java.time.LocalDate;
import java.util.Objects;

import static com.example.cinemabookingsystem.CinemaBookingSystem.moveTheScreen;
import static com.example.cinemabookingsystem.CinemaBookingSystem.removeTheDefaultAntetBar;
import static com.example.cinemabookingsystem.DatabaseConnection.utilizatorConectat;


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
            //check if your username and password is empty
            if(usernameAutentificare.getText().isEmpty() || parolaAutentificare.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare autentificare");
                alert.setHeaderText(null);
                alert.setContentText("Vă rugăm completați toate câmpurile");
                alert.showAndWait();
            }
            else {
                if(result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mesaj de informare");
                    alert.setContentText("Autentificare cu succes!");
                    alert.showAndWait();
                    butonAutentificare.getScene().getWindow().hide();
                    if(!Objects.requireNonNull(utilizatorConectat(connect, usernameAutentificare.getText(), parolaAutentificare.getText())).isAdmin()) {
                        String username = (usernameAutentificare.getText());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MeniuUser.fxml"));
                        Parent root = loader.load();
                        MeniuUserController meniuUserController = loader.getController();
                        meniuUserController.displayUsername(username);
                        meniuUserController.alegeFormatul();
                        meniuUserController.alegeGenul();
                        meniuUserController.getCalendar().setDayCellFactory(meniuUserController.dayCellFactory);
                        meniuUserController.generateGrid(LocalDate.now());
                        meniuUserController.getUtilizatorConectat().setUsername(usernameAutentificare.getText());
                        meniuUserController.getUtilizatorConectat().setParola(parolaAutentificare.getText());
                        meniuUserController.getUtilizatorConectat().setNume(Objects.requireNonNull(utilizatorConectat(connect, usernameAutentificare.getText(), parolaAutentificare.getText())).getNume());
                        meniuUserController.getUtilizatorConectat().setPrenume(Objects.requireNonNull(utilizatorConectat(connect, usernameAutentificare.getText(), parolaAutentificare.getText())).getPrenume());
                        meniuUserController.getUtilizatorConectat().setNrTel(Objects.requireNonNull(utilizatorConectat(connect, usernameAutentificare.getText(), parolaAutentificare.getText())).getNrTel());
                        meniuUserController.getUtilizatorConectat().setEmail(Objects.requireNonNull(utilizatorConectat(connect, usernameAutentificare.getText(), parolaAutentificare.getText())).getEmail());
                        meniuUserController.getUtilizatorConectat().setId(Objects.requireNonNull(utilizatorConectat(connect, usernameAutentificare.getText(), parolaAutentificare.getText())).getId());
                        meniuUserController.getUtilizatorConectat().setAdmin(Objects.requireNonNull(utilizatorConectat(connect, usernameAutentificare.getText(), parolaAutentificare.getText())).isAdmin());

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);

                        moveTheScreen(root, stage);
                        removeTheDefaultAntetBar(stage);

                        meniuUserController.getInapoi().setVisible(false);
                        meniuUserController.getDeconectare().setVisible(true);
                        meniuUserController.getButonContulMeu().setVisible(true);

                        stage.show();
                    } else {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MeniuAdmin.fxml"));
                        Parent root = loader.load();
                        MeniuAdminController meniuAdminController = loader.getController();
                        meniuAdminController.generateGridFilme();
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);


                        moveTheScreen(root, stage);
                        removeTheDefaultAntetBar(stage);

                        stage.show();
                    }


                    //System.out.println(meniuUserController.getCalendar().getValue());

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
