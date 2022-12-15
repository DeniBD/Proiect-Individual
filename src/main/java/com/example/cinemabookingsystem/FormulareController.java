package com.example.cinemabookingsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class FormulareController {
    @FXML
    private Hyperlink autentificareContExistent;

    @FXML
    private Button butonAutentificare;

    @FXML
    private Button butonInregistrare;

    @FXML
    private Button closeAutentificare;

    @FXML
    private Button closeInregistrare;

    @FXML
    private Hyperlink creareCont;

    @FXML
    private TextField emailInregistrare;

    @FXML
    private AnchorPane formularAutentificare;

    @FXML
    private AnchorPane formularInregistrare;

    @FXML
    private Button minimizeAutentificare;

    @FXML
    private Button minimizeInregistrare;

    @FXML
    private PasswordField parolaAutentificare;

    @FXML
    private PasswordField parolaInregistrare;

    @FXML
    private TextField usernameAutentificare;

    @FXML
    private TextField usernameInregistrare;
    private double x = 0;
    private double y = 0;

    // tools for database
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public void closeAutentificare() {
        System.exit(0);
    }

    public void minimizeAutentificare() {
        Stage stage = (Stage) formularAutentificare.getScene().getWindow();
        stage.setIconified(true);

    }

    public void closeInregistrare() {
        System.exit(0);
    }

    public void minimizeInregistrare() {
        Stage stage = (Stage) formularAutentificare.getScene().getWindow();
        stage.setIconified(true);

    }

    public void switchFormular(ActionEvent event) {
        if (event.getSource() == autentificareContExistent) {
            formularInregistrare.setVisible(false);
            formularAutentificare.setVisible(true);
        } else if (event.getSource() == creareCont) {
            formularAutentificare.setVisible(false);
            formularInregistrare.setVisible(true);
        }

    }
    public void autentificare() throws IOException{
        try {

            butonAutentificare.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeniuUser.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
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
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void inregistrare(ActionEvent event) throws IOException {
        if (event.getSource() == butonInregistrare) {
            formularInregistrare.setVisible(false);
            formularAutentificare.setVisible(true);
        }

    }


}