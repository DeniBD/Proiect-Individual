package com.example.cinemabookingsystem;

import com.example.cinemabookingsystem.classes.Film;
import com.example.cinemabookingsystem.classes.Program;
import com.example.cinemabookingsystem.classes.TipFilm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static com.example.cinemabookingsystem.CinemaBookingSystem.moveTheScreen;
import static com.example.cinemabookingsystem.CinemaBookingSystem.removeTheDefaultAntetBar;
import static com.example.cinemabookingsystem.DatabaseConnection.*;


public class MeniuAdminController {
    @FXML
    private AnchorPane meniuAdmin;
    private Connection conn = getConnection();
    private List<Film> filmList = filmList(conn);

    private  List<Program> programsList = programsList(conn);

    private Map<LocalDate, List<Program>> map = getMap();
    @FXML
    private Button butonAdaugaFilme;

    @FXML
    private Button butonAdaugaPrograme;

    @FXML
    private Button butonFilmeMeniuAdmin;

    @FXML
    private Button butonProgrameMeniuAdmin;
    @FXML
    private AnchorPane adaugaFilme;

    @FXML
    private AnchorPane adaugaPrograme;
    @FXML
    private AnchorPane editareStergereFilme;

    @FXML
    private AnchorPane editareStergerePrograme;
    @FXML
    private Button adaugaFilm;
    @FXML
    private TextField durataFilm;
    @FXML
    private TextField titluFilm;
    @FXML
    private TextField genuriFilm;
    @FXML
    private ImageView imgSrcFilm;
    @FXML
    private TextField editareTitluFilm;
    @FXML
    private TextField editareDurataFilm;
    @FXML
    private MenuButton editareGenuriFilm;
    @FXML
    private Button butonEditareFilm;
    @FXML
    private AnchorPane editareFilm;

    public MeniuAdminController() throws SQLException {

    }
    private Map<LocalDate, List<Program>> getMap () {
        //System.out.println(programsList);
        Map<LocalDate, List<Program>> map = new HashMap<>();
        for(Program program : programsList) {
            if(map.containsKey(program.getData())) {
                List<Program> list = new ArrayList<>(map.get(program.getData()));
                list.add(program);
                map.put(program.getData(), list);
            }
            else {
                List<Program> list = new ArrayList<>();
                list.add(program);
                map.put(program.getData(), list);
            }
        }
        //System.out.println(map);
        return map;
    }

    public void OnActionButonMinimizeMeniuAdmin() {
        Stage stage = (Stage) meniuAdmin.getScene().getWindow();
        stage.setIconified(true);

    }
    public void OnActionButonCloseMeniuAdmin() {
        System.exit(0);
    }
    public void deconectare() throws IOException {
        try {
            meniuAdmin.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Index.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            moveTheScreen(root, stage);
            removeTheDefaultAntetBar(stage);

            stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void switchOptions(ActionEvent event) throws SQLException, IOException {
        if (event.getSource() == butonFilmeMeniuAdmin) {
            //filmList = filmList(conn);
            generateGridFilme();
            adaugaFilme.setVisible(false);
            adaugaPrograme.setVisible(false);
            editareStergerePrograme.setVisible(false);
            editareStergereFilme.setVisible(true);
        } else if (event.getSource() == butonProgrameMeniuAdmin) {
            adaugaFilme.setVisible(false);
            adaugaPrograme.setVisible(false);
            editareStergereFilme.setVisible(false);
            editareStergerePrograme.setVisible(true);
        } else if (event.getSource() == butonAdaugaFilme) {
            adaugaPrograme.setVisible(false);
            editareStergereFilme.setVisible(false);
            editareStergerePrograme.setVisible(false);
            adaugaFilme.setVisible(true);
        } else if (event.getSource() == butonAdaugaPrograme) {
            editareStergereFilme.setVisible(false);
            editareStergerePrograme.setVisible(false);
            adaugaFilme.setVisible(false);
            adaugaPrograme.setVisible(true);
        }
    }
    private PreparedStatement prepare;
    public void stergereFilm(int id) {
        String sql1 = "DELETE FROM CALENDAR_PROGRAM WHERE ID_PROGRAM IN (SELECT ID_PROGRAM FROM PROGRAM WHERE ID_FILM = ?)";
        String sql2 = "DELETE FROM PROGRAM WHERE ID_FILM = ?";
        String sql3 = "DELETE FROM GEN WHERE ID_FILM = ?";
        String sql4 = "DELETE FROM FILM WHERE ID_FILM = ?";

        try (PreparedStatement pstmt1 = conn.prepareStatement(sql1);PreparedStatement pstmt2 = conn.prepareStatement(sql2);PreparedStatement pstmt3 = conn.prepareStatement(sql3);PreparedStatement pstmt4 = conn.prepareStatement(sql4)) {

            // set the corresponding param
            pstmt1.setInt(1, id);
            pstmt2.setInt(1, id);
            pstmt3.setInt(1, id);
            pstmt4.setInt(1, id);
            // execute the delete statement
            pstmt1.executeUpdate();
            pstmt2.executeUpdate();
            pstmt3.executeUpdate();
            pstmt4.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void schimbareDetaliiFilm (Film film) {
        // System.out.println(utilizatorConectat);
        editareTitluFilm.setText(film.getTitlu());
        editareDurataFilm.setText(String.valueOf(film.getDurata()));
        String genuri = "";
        for(String gen : film.getGenuri()) {
            genuri += gen + ", ";
        }
        editareGenuriFilm.setText(genuri);

    }
    public void editareFilm(Film film) {
        String sql1 = "UPDATE FILM SET TITLU = ?, DURATA = ? WHERE ID_FILM = " + film.getId();
        String sql2 = "DELETE FROM GEN WHERE ID_FILM = ?";
        try (Statement stmt1 = conn.createStatement()) {
            prepare = conn.prepareStatement(sql1);
            if(! editareTitluFilm.getText().isEmpty()) {
                film.setTitlu(editareTitluFilm.getText());
                prepare.setString(1, editareTitluFilm.getText());
            } else {
                prepare.setString(1, film.getTitlu());
            }
            if(! editareDurataFilm.getText().isEmpty()) {
                film.setDurata(Integer.parseInt(editareDurataFilm.getText()));
                prepare.setString(2, editareDurataFilm.getText());
            } else {
                    prepare.setInt(2, film.getDurata());
            }
            prepare.execute();

            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mesaj de informare");
            alert.setContentText("Editare detalii film facută cu succes!");
            alert.showAndWait();
            editareTitluFilm.setText("");
            editareDurataFilm.setText("");
            editareGenuriFilm.setText("");
            schimbareDetaliiFilm(film);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setOnActionButonStergeFilm(Button sterge, Film film) throws SQLException, IOException {
        sterge.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                filmList.remove(film);
                stergereFilm(film.getId());
                try {
                    generateGridFilme();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    public void  setOnActionButonEditareFilm(Button editare, Film film) {
        editare.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editareStergereFilme.setVisible(false);
                editareFilm.setVisible(true);
                adaugareGenuriMeniuItem();
                butonEditareFilm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        editareFilm(film);
                        try {
                            generateGridFilme();
                        } catch (SQLException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }
    public void adaugareGenuriMeniuItem() {
        String [] genuri = {"Acțiune", "Aventură", "Comedie", "Familie", "Crimă", "Documentar", "Dramă", "Fantastic", "Horror", "Muzical", "Dragoste", "SF", "Thriller"};
        for(String gen : genuri) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(gen);
            editareGenuriFilm.getItems().add(menuItem);
        }
    }
    public void generateGridFilme() throws SQLException, IOException {
        //filmList = filmList(conn);

        BorderPane borderPane = (BorderPane) meniuAdmin.getChildren().get(0);
        AnchorPane anchorPane1 = (AnchorPane) borderPane.getChildren().get(2);
        AnchorPane anchorPane2 = (AnchorPane) anchorPane1.getChildren().get(1);
        VBox vBox = (VBox) anchorPane2.getChildren().get(0);
        ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(0);

        VBox banners = new VBox();
        banners.setPadding(new Insets(30, 0, 30, 30));
        banners.setSpacing(20);

        for(Film film : filmList) {
            HBox banner = new HBox();
            banner.setSpacing(20);

            ImageView imageView = new ImageView(new Image("file:" + film.getImgSrc()));
            imageView.setFitHeight(250);
            imageView.setFitWidth(180);

            // imageView.setOnMouseClicked();

            banner.getChildren().add(imageView);
            VBox detaliiProgram = new VBox();
            detaliiProgram.setSpacing(10);

            Label titlu = new Label(film.getTitlu());
            titlu.setPrefHeight(30);
            titlu.setPrefWidth(450);
            titlu.setFont(new Font(25));
            //titlu.setAlignment(Pos.CENTER);
            String genuri = "";
            for (String gen : film.getGenuri()) {
                genuri += gen + ", ";
            }
            genuri = genuri.substring(0, genuri.lastIndexOf(','));
            Label genuriDurata = new Label(genuri + " | " + film.getDurata() + " min");

            genuriDurata.setFont(new Font(20));

            detaliiProgram.getChildren().addAll(titlu, genuriDurata);
            banner.getChildren().add(detaliiProgram);

            VBox butoane = new VBox();
            butoane.setSpacing(25);

            Button sterge = new Button("Șterge");
            sterge.setPadding(new Insets(10));
            sterge.setStyle("-fx-background-color:#ff8c00");
            sterge.setOnMouseEntered(e -> sterge.setStyle("-fx-background-color: grey"));
            sterge.setOnMouseExited(e -> sterge.setStyle("-fx-background-color: #ff8c00 "));
            butoane.getChildren().add(sterge);
            //banner.getChildren().add(sterge);
            setOnActionButonStergeFilm(sterge, film);

            Button editare = new Button("Editare");
            editare.setPadding(new Insets(10));
            editare.setStyle("-fx-background-color:#ff8c00");
            editare.setOnMouseEntered(e -> editare.setStyle("-fx-background-color: grey"));
            editare.setOnMouseExited(e -> editare.setStyle("-fx-background-color: #ff8c00 "));
            butoane.getChildren().add(editare);
            //banner.getChildren().add(editare);
            setOnActionButonEditareFilm(editare, film);

            butoane.setPadding(new Insets(55));
            banner.getChildren().add(butoane);
            banners.getChildren().add(banner);
        }
        scrollPane.setContent(banners);
    }
}
