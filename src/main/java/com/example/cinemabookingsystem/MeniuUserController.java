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

import static com.example.cinemabookingsystem.DatabaseConnection.getConnection;
import static com.example.cinemabookingsystem.DatabaseConnection.viewTable;

public class MeniuUserController{
    @FXML
    private Button closeMeniuUser;
    @FXML
    private Button minimizeMeniuUser;
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
    private AnchorPane antetProgramFilme;
    @FXML
    private Button deconectareMeniuUser;
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
    private double x = 0;
    private double y = 0;
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
            tabelaPreturi.setVisible(false);
        } else if (event.getSource() == butonContulMeu) {
            programFilme.setVisible(false);
            contulMeu.setVisible(true);
            tabelaPreturi.setVisible(false);
        }
    }
    public void deconectare() throws IOException{
        try {
            meniuUser.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Formulare.fxml")));
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
    @FXML
    private DatePicker datePicker;
    public void getDate(ActionEvent event) {
        LocalDate mydate = datePicker.getValue();
        String myFormattedDate = mydate.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
        System.out.println(myFormattedDate);
    }
    private List<Film> films() {
        List<Film> lf = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Film film = new Film();
            film.setImgSrc("images/avatar.jpg");
            String[] genuri = new String[2];
            genuri[0] = "gen1";
            genuri[1] = "gen2";
            film.setDescriere("fdvsdv");
            film.setGenuri(genuri);
            film.setDurata(120);
            film.setId(i);
            film.setTitlu("film" + i);
            lf.add(film);
        }
        return lf;
    }
    private List<Program> programs() {
        List<Program> lp = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            Program program = new Program(i, filmList.get(i % 10), new Data(10, 8, 2022), 3 + i, 20, TipFilm.DOI_D);
            lp.add(program);
        }
        for(int i = 0; i < 10; i++) {
            Program program = new Program(i, filmList.get(i % 10), new Data(10, 8, 2022), 3 + i, 20, TipFilm.TREI_D);
            lp.add(program);
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



        VBox fin = new VBox();
        fin.setPadding(new Insets(20, 0, 20, 20));
        fin.setSpacing(10);

        for(Map.Entry<Film, List<Program>> m : programe.entrySet()) {
            HBox item = new HBox();
            item.setSpacing(10);

            ImageView imageView = new ImageView(new Image("file:images/avatar.jpg"));
            imageView.setFitHeight(326);
            imageView.setFitWidth(220);

            // imageView.setOnMouseClicked();

            item.getChildren().add(imageView);

            Film f = m.getKey();
            List<Program> l = m.getValue();

            VBox detaliiProgram = new VBox();
            detaliiProgram.setSpacing(10);

            Label titlu = new Label(f.getTitlu());
            titlu.setPrefHeight(50);
            titlu.setPrefWidth(300);
            titlu.setFont(new Font(30));
            titlu.setAlignment(Pos.CENTER);
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
                HBox ore = new HBox();
                ore.setSpacing(10);

                for(Program pr : l) {
                    if(pr.getTipFilm() == film.getKey()) {
                        Button button = new Button(pr.getOraInceput() + ":" + pr.getMinutInceput());
                        button.setPadding(new Insets(10));
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                String titlu = f.getTitlu() + " " + (pr.getTipFilm() == TipFilm.DOI_D ? "2D": "3D");
                                titluFilmDinProgram.setText(titlu);

                                String data = pr.getData().toString() + " " + pr.getOraInceput() + ":" + pr.getMinutInceput();
                                dataFilmDinProgram.setText(data);

                                try {
                                    List<Pret> preturi = viewTable(conn);
                                    for(Pret pret : preturi) {
                                        if(pr.getTipFilm() == TipFilm.DOI_D && pret.getTip_film().equals("2D")) {
                                            tipClientCol.getColumns().add(new TableColumn<>(pret.getTip_bilet()));
                                        } else if(pr.getTipFilm() == TipFilm.TREI_D && pret.getTip_film().equals("3D")) {
                                            tipClientCol.getColumns().add(new TableColumn<>(pret.getTip_bilet()));                                        }
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }

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

            fin.getChildren().add(item);
        }

        scrollPane.setContent(fin);
    }

}
