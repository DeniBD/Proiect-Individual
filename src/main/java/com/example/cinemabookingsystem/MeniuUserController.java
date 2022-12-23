package com.example.cinemabookingsystem;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private Button butonCoduriReducere;
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
    private TableView<Pret> tabelPreturi;
    @FXML
    private TableColumn<Pret, String> tipClientCol;
    @FXML
    private TableColumn<Pret, Double> pretCol;
    @FXML
    private Label username;
    @FXML
    private AnchorPane deconectare;
    @FXML
    private AnchorPane inapoi;

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
            generateGrid();
            contulMeu.setVisible(false);
            programFilme.setVisible(true);
            //tabelaPreturi.setVisible(false);
        } else if (event.getSource() == butonContulMeu) {
            programFilme.setVisible(false);
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
    private List<Film> films() throws SQLException {
        //List<Film> lf = new ArrayList<>(filmList(conn));
        List<Film> lf = new ArrayList<>();
        Film film1 = new Film();
        film1.setImgSrc("images/avatar.jpg");
        String[] genuri1 = new String[]{"SF", "Acțiune", "Aventuri"};
        film1.setTitlu("Avatar");
        film1.setGenuri(genuri1);
        film1.setDurata(120);
        film1.setId(1);
        film1.setTitlu(film1.getTitlu());
        lf.add(film1);

        Film film2 = new Film();
        film2.setImgSrc("images/pinocchio.jpg");
        String[] genuri2 = new String[]{"Aventură", "Familie"};
        film2.setTitlu("Pinocchio");
        film2.setGenuri(genuri2);
        film2.setDurata(200);
        film2.setId(2);
        film2.setTitlu(film2.getTitlu());
        lf.add(film2);

        Film film3 = new Film();
        film3.setImgSrc("images/thor.jpg");
        String[] genuri3 = new String[]{"Acțiune", "Fantezie", "Comedie"};
        film3.setTitlu("Thor: Iubire şi tunete");
        film3.setGenuri(genuri3);
        film3.setDurata(250);
        film3.setId(3);
        film3.setTitlu(film3.getTitlu());
        lf.add(film3);

        Film film4 = new Film();
        film4.setImgSrc("images/grinch.jpg");
        String[] genuri4 = new String[]{"Animatie", "Familie", "Comedie"};
        film4.setTitlu("Grinch");
        film4.setGenuri(genuri4);
        film4.setDurata(115);
        film4.setId(3);
        film4.setTitlu(film4.getTitlu());
        lf.add(film4);

        Film film5 = new Film();
        film5.setImgSrc("images/harryp.jpg");
        String[] genuri5 = new String[]{"Aventură", "Fantezie"};
        film5.setTitlu("Harry Potter");
        film5.setGenuri(genuri4);
        film5.setDurata(115);
        film5.setId(3);
        film5.setTitlu(film5.getTitlu());
        lf.add(film5);

        Film film6 = new Film();
        film6.setImgSrc("images/ffc.jpg");
        String[] genuri6 = new String[]{"Romantic", "Comedie", "Familie"};
        film6.setTitlu("Falling for Christmas");
        film6.setGenuri(genuri6);
        film6.setDurata(120);
        film6.setId(1);
        film6.setTitlu(film6.getTitlu());
        lf.add(film6);

        Film film7 = new Film();
        film7.setImgSrc("images/jurassic.jpg");
        String[] genuri7 = new String[]{"Aventură", "Actiune"};
        film7.setTitlu("Jurassic World: Dominația");
        film7.setGenuri(genuri6);
        film7.setDurata(120);
        film7.setId(1);
        film7.setTitlu(film7.getTitlu());
        lf.add(film7);

        return lf;
    }
    private List<Program> programs() {
        List<Program> lp = new ArrayList<>();
        int m = 10;
        for(int i = 0; i < 7; i++) {
            Program program = new Program(i, filmList.get(i), new Data(8, 12, 2022), 15 + i, 20 + i * m, TipFilm.DOI_D);
            Program program2 = new Program(i, filmList.get(i), new Data(8, 12, 2022), 13 + i, 20 + i * m, TipFilm.DOI_D);
            Program program3 = new Program(i, filmList.get(i), new Data(8, 12, 2022), 12 + i, 20 + i * m, TipFilm.DOI_D);
            Program program4 = new Program(i, filmList.get(i), new Data(8, 12, 2022), 17 + i, 20 + i * m, TipFilm.DOI_D);
            lp.add(program);
            lp.add(program2);
            lp.add(program3);
            lp.add(program4);
        }
        m = 5;
        for(int i = 3; i < 7; i++) {
            Program program = new Program(i, filmList.get(i), new Data(8, 12, 2022), 15 + i, 20 + i * (m+5), TipFilm.TREI_D);
            Program program2 = new Program(i, filmList.get(i), new Data(8, 12, 2022), 19 + i, 20 + i * (m), TipFilm.TREI_D);
            lp.add(program);
            lp.add(program2);
        }
        return lp;
    }
    private Map<Film, List<Program>> maps() {
        Map<Film, List<Program>> map = new HashMap<>();
        for(Program pr : programList) {
            if(map.containsKey(pr.getFilm()) == false) {
                List<Program> tmp = new ArrayList<>(){{add(pr);}};
                map.put(pr.getFilm(), tmp);
            } else {
                List<Program> tmp = new ArrayList<>(map.get(pr.getFilm()));
                tmp.add(pr);
                map.put(pr.getFilm(), tmp);
            }
        }
//        map.forEach((k, v) -> {
//            System.out.println(k);
//            System.out.println(v);
//        });
        return map;
    }
    private List<Film> filmList = films();
    private List<Program> programList = programs();
    private Map<Film, List<Program>> programe = maps();
    private Connection conn = getConnection();
    private void generateGrid() {
        // GridPane gridPane = new GridPane();

        BorderPane borderPane = (BorderPane) meniuUser.getChildren().get(0);
        AnchorPane anchorPane1 = (AnchorPane) borderPane.getChildren().get(2);
        AnchorPane anchorPane2 = (AnchorPane) anchorPane1.getChildren().get(1);
        VBox vBox = (VBox) anchorPane2.getChildren().get(0);
        ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(0);

        VBox banners = new VBox();
        banners.setPadding(new Insets(30, 0, 30, 30));
        banners.setSpacing(20);

        for(Map.Entry<Film, List<Program>> m : programe.entrySet()) {
            HBox item = new HBox();
            item.setSpacing(20);

            ImageView imageView = new ImageView(new Image("file:" + m.getKey().getImgSrc()));
            imageView.setFitHeight(250);
            imageView.setFitWidth(180);

            // imageView.setOnMouseClicked();

            item.getChildren().add(imageView);

            Film f = m.getKey();
            List<Program> l = m.getValue();

            VBox detaliiProgram = new VBox();
            //detaliiProgram.setSpacing(10);

            Label titlu = new Label(f.getTitlu());
            titlu.setPrefHeight(30);
            titlu.setPrefWidth(400);
            titlu.setFont(new Font(30));
            //titlu.setAlignment(Pos.CENTER);
            String genuri = "";
            for(String gen : f.getGenuri()) {
                genuri += gen + ", ";
            }
            genuri = genuri.substring(0, genuri.lastIndexOf(','));
            Label genuriDurata = new Label(genuri + " | " + f.getDurata() + " min");

            genuriDurata.setFont(new Font(20));

            detaliiProgram.getChildren().addAll(titlu, genuriDurata);

            Map<TipFilm, Integer> tempMap = new HashMap<>();
            for(Program pr : l) {
                if(tempMap.containsKey(pr.getTipFilm()) == false) {
                    tempMap.put(pr.getTipFilm(), 1);
                } else {
                    tempMap.put(pr.getTipFilm(), tempMap.get(pr.getTipFilm()) + 1);
                }
            }

            VBox panouTipuriFilme = new VBox();
            for(Map.Entry<TipFilm, Integer> film : tempMap.entrySet()) {
                VBox panouTip = new VBox();

                Label tip = new Label(film.getKey() == TipFilm.DOI_D ? "2D" : "3D");
                tip.setFont(Font.font(12));
                tip.setPadding(new Insets(10));
                HBox ore = new HBox();
                ore.setSpacing(10);

                for(Program pr : l) {
                    if(pr.getTipFilm() == film.getKey()) {
                        Button button = new Button(pr.getOraInceput() + ":" + pr.getMinutInceput());
                        button.setPadding(new Insets(10));
                        button.setStyle("-fx-background-color:#ff8c00");
                        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: grey"));
                        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #ff8c00 "));
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
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
                                tabelaPreturi.setVisible(true);
                                programFilme.setVisible(false);
                            }

                        });

                        ore.getChildren().add(button);

                    }
                }

                panouTip.getChildren().addAll(tip, ore);

                panouTipuriFilme.getChildren().add(panouTip);
            }

            detaliiProgram.getChildren().add(panouTipuriFilme);

            item.getChildren().add(detaliiProgram);

            banners.getChildren().add(item);
        }

        scrollPane.setContent(banners);
    }

}
