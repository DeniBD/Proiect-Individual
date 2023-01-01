package com.example.cinemabookingsystem;


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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.cinemabookingsystem.CinemaBookingSystem.moveTheScreen;
import static com.example.cinemabookingsystem.CinemaBookingSystem.removeTheDefaultAntetBar;
import static com.example.cinemabookingsystem.DatabaseConnection.*;

public class MeniuUserController{
    @FXML
    private Button butonContulMeu;
    @FXML
    private Button butonProgramFilme;
    @FXML
    private AnchorPane contulMeu;
    @FXML
    private AnchorPane programFilme;
    @FXML
    private AnchorPane meniuUser;
    @FXML
    private Label titluFilmDinProgram;
    @FXML
    private Label dataFilmDinProgram;
    @FXML
    private AnchorPane tabelaPreturi;
    @FXML
    private Label username;
    @FXML
    private AnchorPane deconectare;
    @FXML
    private AnchorPane inapoi;
    @FXML
    private Label pensionar;
    @FXML
    private Label copil;
    @FXML
    private Label adult;
    @FXML
    private Label elev;
    @FXML
    private Label student;
    @FXML
    private Label pretAdult;

    @FXML
    private Label pretCopil;

    @FXML
    private Label pretElev;

    @FXML
    private Label pretPensionar;

    @FXML
    private Label pretStudent;
    @FXML
    private MenuButton cantitateAdult;

    @FXML
    private MenuButton cantitateCopil;

    @FXML
    private MenuButton cantitateElev;

    @FXML
    private MenuButton cantitatePensionar;

    @FXML
    private MenuButton cantitateStudent;

    public MeniuUserController() throws SQLException {
    }

    public AnchorPane getInapoi() {
        return inapoi;
    }

    public Button getButonContulMeu() {
        return butonContulMeu;
    }

    public AnchorPane getDeconectare() {
        return deconectare;
    }

    public void displayUsername(String u) {
        username.setText(u);
    }
    public void closeMeniuUser() {
        System.exit(0);
    }
    public void minimizeMeniuUser() {
        Stage stage = (Stage) meniuUser.getScene().getWindow();
        stage.setIconified(true);

    }
    public void switchOptions(ActionEvent event) {
        if (event.getSource() == butonProgramFilme) {
            LocalDate date = LocalDate.of(2023, 1, 8);    ;
            generateGrid(date);
            contulMeu.setVisible(false);
            tabelaPreturi.setVisible(false);
            programFilme.setVisible(true);

            //tabelaPreturi.setVisible(false);
        } else if (event.getSource() == butonContulMeu) {
            programFilme.setVisible(false);
            tabelaPreturi.setVisible(false);
            contulMeu.setVisible(true);
            //tabelaPreturi.setVisible(false);
        }
    }
    public void deconectare() throws IOException{
        try {
            username.setText("");
            meniuUser.getScene().getWindow().hide();
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
    public void inapoi() throws IOException {
        try {
            meniuUser.getScene().getWindow().hide();
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
    @FXML
    private DatePicker datePicker;
    public void getDate(ActionEvent event) {
        LocalDate mydate = datePicker.getValue();
        String myFormattedDate = mydate.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
        System.out.println(myFormattedDate);
    }
    private Connection conn = getConnection();
    private List<Film> filmList = filmList(conn);
    List<Pret> pretList = pretList(conn);
    private  List<Program> programsList = programsList(conn);
    private Map<LocalDate, List<Program>> map = getMap();
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
    private List<Film> getFilmsListOnDateX (LocalDate date) {
        List<Film> filme = new ArrayList<>();
        for(Program program : map.get(date)) {
            boolean ok = true;
            for(Film film : filme) {
                if(program.getFilm().getId() == film.getId())
                    ok = false;
            }
            if(ok == true)
                filme.add(program.getFilm());

        }
        return filme;
    }
    private Map<TipFilm, Integer> getTipFilmIntegerMap (Film film, List<Program> programs) {
        Map<TipFilm, Integer> map = new HashMap<>();
        for (Program program : programs) {
            if (film.getId() == program.getFilm().getId()) {
                if (!map.containsKey(program.getTipFilm())) {
                    map.put(program.getTipFilm(), 1);
                } else {
                    map.put(program.getTipFilm(), map.get(program.getTipFilm()) + 1);
                }
            }
        }
        return map;
    }
    private void setOnActionButonFilmDinProgram(Button button, Film film, Program program) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                programFilme.setVisible(false);
                tabelaPreturi.setVisible(true);
                String titlu = film.getTitlu() + " " + (program.getTipFilm() == TipFilm.DOI_D ? "2D": "3D");
                titluFilmDinProgram.setText(titlu);
                String data = program.getData().toString() + " " + program.getOraInceput() + ":" + (program.getMinutInceput() == 0 ? "00" : program.getMinutInceput());
                dataFilmDinProgram.setText(data);
                for(Pret pret : pretList) {
                    if(pret.getTip_film() == program.getTipFilm()) {
                        copil.setText((program.getTipFilm() == TipFilm.DOI_D ? "2D": "3D"));
                        elev.setText((program.getTipFilm() == TipFilm.DOI_D ? "2D": "3D"));
                        student.setText((program.getTipFilm() == TipFilm.DOI_D ? "2D": "3D"));
                        adult.setText((program.getTipFilm() == TipFilm.DOI_D ? "2D": "3D"));
                        pensionar.setText((program.getTipFilm() == TipFilm.DOI_D ? "2D": "3D"));
                        if(pret.getTip_bilet() == TipUtilizator.COPIL)
                            pretCopil.setText(String.valueOf(pret.getPret()));
                        if(pret.getTip_bilet() == TipUtilizator.ELEV)
                            pretElev.setText(String.valueOf(pret.getPret()));
                        if(pret.getTip_bilet() == TipUtilizator.STUDENT)
                            pretStudent.setText(String.valueOf(pret.getPret()));
                        if(pret.getTip_bilet() == TipUtilizator.ADULT)
                            pretAdult.setText(String.valueOf(pret.getPret()));
                        if(pret.getTip_bilet() == TipUtilizator.PENSIONAR)
                            pretPensionar.setText(String.valueOf(pret.getPret()));
                    }
                }
                for(int i = 1; i <= program.getNrBileteDisponibile(); i++) {
                    MenuItem item = new MenuItem();
                    item.setText(String.valueOf(i));
                }


                                /*
                                String titlu = f.getTitlu() + " " + (pr.getTipFilm() == TipFilm.DOI_D ? "2D": "3D");
                                titluFilmDinProgram.setText(titlu);
                                String data = pr.getData().toString() + " " + pr.getOraInceput() + ":" + pr.getMinutInceput();
                                dataFilmDinProgram.setText(data);
                                try {
                                    List<Pret> preturi = pretList(conn);
                                    for(Pret pret : preturi) {
                                        if(pr.getTipFilm() == TipFilm.DOI_D && pret.getTip_film().equals("2D")) {
                                            tipClientCol.getColumns().add(new TableColumn<>(pret.getTip_bilet()));
                                        } else if(pr.getTipFilm() == TipFilm.TREI_D && pret.getTip_film().equals("3D")) {
                                            tipClientCol.getColumns().add(new TableColumn<>(pret.getTip_bilet()));                                        }
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                */
            }

        });
    }
    private void generateGrid(LocalDate date) {
        GridPane gridPane = new GridPane();

        BorderPane borderPane = (BorderPane) meniuUser.getChildren().get(0);
        AnchorPane anchorPane1 = (AnchorPane) borderPane.getChildren().get(2);
        AnchorPane anchorPane2 = (AnchorPane) anchorPane1.getChildren().get(1);
        VBox vBox = (VBox) anchorPane2.getChildren().get(0);
        ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(0);

        VBox banners = new VBox();
        banners.setPadding(new Insets(30, 0, 30, 30));
        banners.setSpacing(20);

        for(Film film : getFilmsListOnDateX(date)) {
            HBox banner = new HBox();
            banner.setSpacing(20);

            ImageView imageView = new ImageView(new Image("file:" + film.getImgSrc()));
            imageView.setFitHeight(250);
            imageView.setFitWidth(180);

            // imageView.setOnMouseClicked();

            banner.getChildren().add(imageView);
            VBox detaliiProgram = new VBox();
            //detaliiProgram.setSpacing(10);

            Label titlu = new Label(film.getTitlu());
            titlu.setPrefHeight(30);
            titlu.setPrefWidth(400);
            titlu.setFont(new Font(30));
            //titlu.setAlignment(Pos.CENTER);
            String genuri = "";
            for (String gen : film.getGenuri()) {
                genuri += gen + ", ";
            }
            genuri = genuri.substring(0, genuri.lastIndexOf(','));
            Label genuriDurata = new Label(genuri + " | " + film.getDurata() + " min");

            genuriDurata.setFont(new Font(20));

            detaliiProgram.getChildren().addAll(titlu, genuriDurata);
            VBox panouTipuriFilme = new VBox();
            for (Map.Entry<TipFilm, Integer> tipFilm : getTipFilmIntegerMap(film, map.get(date)).entrySet()) {
                if(tipFilm.getValue() != 0) {
                    VBox panouTip = new VBox();
                    Label tip = new Label(tipFilm.getKey() == TipFilm.DOI_D ? "2D" : "3D");
                    tip.setFont(Font.font(12));
                    tip.setPadding(new Insets(10));
                    HBox ore = new HBox();
                    ore.setSpacing(10);

                    for (Program program : map.get(date)) {
                        if (program.getFilm().getId() == film.getId()) {
                            if (program.getTipFilm() == tipFilm.getKey()) {
                                Button button = new Button(program.getOraInceput() + ":" + (program.getMinutInceput() == 0 ? "00" : program.getMinutInceput()));
                                button.setPadding(new Insets(10));
                                button.setStyle("-fx-background-color:#ff8c00");
                                button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: grey"));
                                button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ff8c00 "));
                                ore.getChildren().add(button);
                                setOnActionButonFilmDinProgram(button, film , program);

                            }
                        }
                    }
                    panouTip.getChildren().addAll(tip, ore);
                    panouTipuriFilme.getChildren().add(panouTip);
            }
        }
            detaliiProgram.getChildren().add(panouTipuriFilme);
            banner.getChildren().add(detaliiProgram);
            banners.getChildren().add(banner);
        }
        scrollPane.setContent(banners);
    }

}
