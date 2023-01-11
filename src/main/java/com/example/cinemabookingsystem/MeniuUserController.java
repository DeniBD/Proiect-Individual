package com.example.cinemabookingsystem;


import com.example.cinemabookingsystem.classes.*;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static com.example.cinemabookingsystem.CinemaBookingSystem.moveTheScreen;
import static com.example.cinemabookingsystem.CinemaBookingSystem.removeTheDefaultAntetBar;
import static com.example.cinemabookingsystem.DatabaseConnection.*;

public class MeniuUserController{
    @FXML
    private Button r1c1, r1c2, r1c3, r1c4, r1c5, r1c6, r1c7, r1c8, r1c9, r1c10;
    @FXML
    private Button r2c1, r2c2, r2c3, r2c4, r2c5, r2c6, r2c7, r2c8, r2c9, r2c10;
    @FXML
    private Button r3c1, r3c2, r3c3, r3c4, r3c5, r3c6, r3c7, r3c8, r3c9, r3c10;
    @FXML
    private Button r4c1, r4c2, r4c3, r4c4, r4c5, r4c6, r4c7, r4c8, r4c9, r4c10;
    @FXML
    private Button r5c1, r5c2, r5c3, r5c4, r5c5, r5c6, r5c7, r5c8, r5c9, r5c10;
    @FXML
    private Button r6c1, r6c2, r6c3, r6c4, r6c5, r6c6, r6c7, r6c8, r6c9, r6c10;
    @FXML
    private Button r7c1, r7c2, r7c3, r7c4, r7c5, r7c6, r7c7, r7c8, r7c9, r7c10;
    private List<Button> butoaneLocuriSala;
    private List<Button> butoaneLocuriOcupate;
    private List<Button> butoaneLocuriSelectate;

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
    private ScrollPane tabelaPreturi;
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
    private MenuButton alegeFormatul;
    @FXML
    private MenuButton alegeGenul;

    @FXML
    private MenuButton cantitatePensionar;

    @FXML
    private MenuButton cantitateStudent;
    @FXML
    private DatePicker calendar;
    @FXML
    private AnchorPane sala;
    @FXML
    private Label LabelTotalLocuriDeSelectat;
    @FXML
    private TextField detaliiContEmail;

    @FXML
    private TextField detaliiContNrTel;

    @FXML
    private TextField detaliiContParola;

    @FXML
    private TextField detaliiContUsername;

    @FXML
    private AnchorPane formularPlata;
    @FXML
    private RadioButton radioButton;
    @FXML
    private TextField CVV2;
    @FXML
    private TextField dataExpirare;
    @FXML
    private TextField numarCard;
    @FXML
    private Button butonFinalizareComanda;
    @FXML
    private TextField detaliiContNume;
    @FXML
    private TextField detaliiContPrenume;
    @FXML
    private Button salveazaDetaliiCont;

    private Connection conn = getConnection();
    private int locuriDeSelectat = 0;
    private List<Film> filmList = filmList(conn);
    private List<Pret> pretList = pretList(conn);
    private  List<Program> programsList = programsList(conn);
    private Map<LocalDate, List<Program>> map = getMap();
    private Utilizator utilizatorConectat = new Utilizator();
    private Program programAles = new Program();
    public void setProgramAles(Program programAles) throws SQLException {
        this.programAles.setId(programAles.getId());
        this.programAles.setData(programAles.getData());
        this.programAles.setFilm(programAles.getFilm());
        this.programAles.setMinutInceput(programAles.getMinutInceput());
        this.programAles.setOraInceput(programAles.getOraInceput());
        this.programAles.setNrBileteDisponibile(programAles.getNrBileteDisponibile());
        this.programAles.setTipFilm(programAles.getTipFilm());
        this.programAles.setSalaFilm(programAles.getSalaFilm());
    }
    public void setUtilizatorConectat(Utilizator utilizatorConectat) {
        this.utilizatorConectat = utilizatorConectat;
    }
    public Utilizator getUtilizatorConectat() {
        return utilizatorConectat;
    }

    public DatePicker getCalendar() {
        return calendar;
    }
    public void totalLocuriDeSelectat() {
        this.locuriDeSelectat = 0;
        this.locuriDeSelectat += Integer.parseInt(cantitateCopil.getText());
        this.locuriDeSelectat += Integer.parseInt(cantitateElev.getText());
        this.locuriDeSelectat += Integer.parseInt(cantitateStudent.getText());
        this.locuriDeSelectat += Integer.parseInt(cantitateAdult.getText());
        this.locuriDeSelectat += Integer.parseInt(cantitatePensionar.getText());
    }

    public MeniuUserController() throws SQLException {
        try {
            pretList = pretList(conn);
            filmList = filmList(conn);
            programsList = programsList(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generareListaLocuriSala() {
        butoaneLocuriSala = new ArrayList<>();
            butoaneLocuriSala.add(r1c1); butoaneLocuriSala.add(r1c2); butoaneLocuriSala.add(r1c3); butoaneLocuriSala.add(r1c4); butoaneLocuriSala.add(r1c5); butoaneLocuriSala.add(r1c6); butoaneLocuriSala.add(r1c7); butoaneLocuriSala.add(r1c8); butoaneLocuriSala.add(r1c9); butoaneLocuriSala.add(r1c10);
            butoaneLocuriSala.add(r2c1); butoaneLocuriSala.add(r2c2); butoaneLocuriSala.add(r2c3); butoaneLocuriSala.add(r2c4); butoaneLocuriSala.add(r2c5); butoaneLocuriSala.add(r2c6); butoaneLocuriSala.add(r2c7); butoaneLocuriSala.add(r2c8); butoaneLocuriSala.add(r2c9); butoaneLocuriSala.add(r2c10);
            butoaneLocuriSala.add(r3c1); butoaneLocuriSala.add(r3c2); butoaneLocuriSala.add(r3c3); butoaneLocuriSala.add(r3c4); butoaneLocuriSala.add(r3c5); butoaneLocuriSala.add(r3c6); butoaneLocuriSala.add(r3c7); butoaneLocuriSala.add(r3c8); butoaneLocuriSala.add(r3c9); butoaneLocuriSala.add(r3c10);
            butoaneLocuriSala.add(r4c1); butoaneLocuriSala.add(r4c2); butoaneLocuriSala.add(r4c3); butoaneLocuriSala.add(r4c4); butoaneLocuriSala.add(r4c5); butoaneLocuriSala.add(r4c6); butoaneLocuriSala.add(r4c7); butoaneLocuriSala.add(r4c8); butoaneLocuriSala.add(r4c9); butoaneLocuriSala.add(r4c10);
            butoaneLocuriSala.add(r5c1); butoaneLocuriSala.add(r5c2); butoaneLocuriSala.add(r5c3); butoaneLocuriSala.add(r5c4); butoaneLocuriSala.add(r5c5); butoaneLocuriSala.add(r5c6); butoaneLocuriSala.add(r5c7); butoaneLocuriSala.add(r5c8); butoaneLocuriSala.add(r5c9); butoaneLocuriSala.add(r5c10);
            butoaneLocuriSala.add(r6c1); butoaneLocuriSala.add(r6c2); butoaneLocuriSala.add(r6c3); butoaneLocuriSala.add(r6c4); butoaneLocuriSala.add(r6c5); butoaneLocuriSala.add(r6c6); butoaneLocuriSala.add(r6c7); butoaneLocuriSala.add(r6c8); butoaneLocuriSala.add(r6c9); butoaneLocuriSala.add(r6c10);
            butoaneLocuriSala.add(r7c1); butoaneLocuriSala.add(r7c2); butoaneLocuriSala.add(r7c3); butoaneLocuriSala.add(r7c4); butoaneLocuriSala.add(r7c5); butoaneLocuriSala.add(r7c6); butoaneLocuriSala.add(r7c7); butoaneLocuriSala.add(r7c8); butoaneLocuriSala.add(r7c9); butoaneLocuriSala.add(r7c10);
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
    public void OnActionButonCloseMeniuUser() {
        System.exit(0);
    }
    public void OnActionButonFiltreaza() {
        LocalDate date = getDate();
        System.out.println(getDate());
        String tipFilm = alegeFormatul.getText();
        String gen = alegeGenul.getText();
        gridFiltrat(date, tipFilm, gen);
        alegeGenul.setText("ALEGE GENUL");
        alegeFormatul.setText("ALEGE FORMATUL");

    }
    public void gridFiltrat(LocalDate date, String tF, String g) {
        GridPane gridPane = new GridPane();

        BorderPane borderPane = (BorderPane) meniuUser.getChildren().get(0);
        AnchorPane anchorPane1 = (AnchorPane) borderPane.getChildren().get(2);
        AnchorPane anchorPane2 = (AnchorPane) anchorPane1.getChildren().get(1);
        VBox vBox = (VBox) anchorPane2.getChildren().get(0);
        ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(0);

        VBox banners = new VBox();
        banners.setPadding(new Insets(30, 0, 30, 30));
        banners.setSpacing(20);

        List<Film> filmeFiltrate = new ArrayList<>(getFilmsListOnDateXAndGenY(date, tF));

        if(!g.equals("ALEGE GENUL") && !g.equals("Toate genurile")) {
            for(int i = 0; i < filmeFiltrate.size(); i++) {
                boolean ok = false;
                for (String gen : filmeFiltrate.get(i).getGenuri()) {
                    if (g.equals(gen)) {
                        ok = true;
                    }
                }
                if(ok == false) {
                    filmeFiltrate.remove(i);
                    i--;
                }
            }
        }

        for(Film film : filmeFiltrate) {
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
            titlu.setPrefWidth(600);
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
            VBox panouTipuriFilme = new VBox();

            Map<TipFilm, Integer> tipuriFilme = getTipFilmIntegerMap(film, map.get(date));
            if(tF.equals("2D")) {
                for(Map.Entry<TipFilm, Integer> tipFilm : tipuriFilme.entrySet()) {
                    if(tipFilm.getKey() == TipFilm.TREI_D) {
                        tipuriFilme.put(TipFilm.TREI_D, 0);
                    }
                }
            } else if(tF.equals("3D")) {
                for(Map.Entry<TipFilm, Integer> tipFilm : tipuriFilme.entrySet()) {
                    if(tipFilm.getKey() == TipFilm.DOI_D) {
                        tipuriFilme.put(TipFilm.DOI_D, 0);
                    }
                }
            }

            for (Map.Entry<TipFilm, Integer> tipFilm : tipuriFilme.entrySet()) {
                if(tipFilm.getValue() != 0) {
                    VBox panouTip = new VBox();
                    Label tip = new Label(tipFilm.getKey() == TipFilm.DOI_D ? "2D" : "3D");
                    tip.setPadding(new Insets(10));
                    tip.setFont(Font.font("System", FontWeight.BOLD, 12));
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
                                if(!username.getText().equals("")) {
                                    setOnActionButonFilmDinProgram(button, film, program);
                                }
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
    final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
        List<LocalDate> dateProgram = new ArrayList<>(dateProgram());
        @Override
        public DateCell call(final DatePicker datePicker) {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if(!empty) {
                        if(dateProgram.contains(item)) {
                            setStyle("-fx-background-color: #778899");
                        }
                    }
                }
            };
        }
    };
    public List<LocalDate> dateProgram() {
        List<LocalDate> dateProgram = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Program>> data : map.entrySet()) {
            dateProgram.add(data.getKey());
        }
        return dateProgram;
    }
    public void OnActionButonSelecteazaLocurile() {
        totalLocuriDeSelectat();
        if(locuriDeSelectat != 0) {
            generareListaLocuriSala();

            butoaneLocuriOcupate = new ArrayList<>();

            String tmp1 = programAles.getSalaFilm().toString();
            String[] locuri = tmp1.split(", ", 0);
            for(int i = 0; i < locuri.length; i++) {
                if(locuri[i].equals("true")) {
                    butoaneLocuriOcupate.add(butoaneLocuriSala.get(i));
                }
            }

            butoaneLocuriSelectate = new ArrayList<>();
            setOnActionButoaneSala();

            tabelaPreturi.setVisible(false);
            sala.setVisible(true);
            LabelTotalLocuriDeSelectat.setText("Te rog selectează " + locuriDeSelectat + " locuri!");
        }
    }

    public void setOnActionButoaneSala() {
        for(Button button : butoaneLocuriSala) {
            if(butoaneLocuriOcupate.contains(button)) {
                //  Grey Button
                button.setStyle("-fx-background-color: grey");
            } else {
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(butoaneLocuriSelectate.contains(button)) {
                            //  Green Button
                            button.setStyle("-fx-background-color: green");
                            butoaneLocuriSelectate.remove(button);
                        } else {
                            if (butoaneLocuriSelectate.size() >= locuriDeSelectat) {
                                //  Green Button
                                butoaneLocuriSelectate.get(0).setStyle("-fx-background-color: green");
                                butoaneLocuriSelectate.remove(0);
                            }
                            //  Yellow Button
                            button.setStyle("-fx-background-color: orange");
                            butoaneLocuriSelectate.add(button);
                        }
                    }
                });
            }
        }
    }
    public void OnActionButonPasulAnterior() {
        sala.setVisible(false);
        tabelaPreturi.setVisible(true);
    }
    public void OnActionButonPasulUrmator() {
        sala.setVisible(false);
        formularPlata.setVisible(true);
    }
    public void OnActionButonMinimizeMeniuUser() {
        Stage stage = (Stage) meniuUser.getScene().getWindow();
        stage.setIconified(true);

    }

    public void resetCantitate () {
        cantitateCopil.setText("0");
        cantitateElev.setText("0");
        cantitateStudent.setText("0");
        cantitateAdult.setText("0");
        cantitatePensionar.setText("0");
    }
    public void switchOptions(ActionEvent event) throws SQLException {
        if (event.getSource() == butonProgramFilme) {
            LocalDate date = LocalDate.of(2023, 1, 8);
            pretList = pretList(conn);
            filmList = filmList(conn);
            pretList = pretList(conn);
            generateGrid(date);
            contulMeu.setVisible(false);
            tabelaPreturi.setVisible(false);
            programFilme.setVisible(true);
            sala.setVisible(false);
            formularPlata.setVisible(false);
            resetCantitate();
            locuriDeSelectat = 0;
            numarCard.setText("");
            dataExpirare.setText("");
            CVV2.setText("");
        } else if (event.getSource() == butonContulMeu) {
            programFilme.setVisible(false);
            tabelaPreturi.setVisible(false);
            contulMeu.setVisible(true);
            formularPlata.setVisible(false);
            sala.setVisible(false);
            schimbareDetaliiContSetDefaultText(utilizatorConectat);
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
    public LocalDate getDate() {
        LocalDate selectedDate = calendar.getValue();
        //System.out.println(selectedDate);
        if(selectedDate == null)
            return LocalDate.now();
        return selectedDate;
        //String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public void alegeFormatul() {
        List<TipFilm> tipFilmeDataX = tipuriFilme();
        for (TipFilm tipFilm : tipFilmeDataX) {
            MenuItem item = new MenuItem();
            item.setText(tipFilm == TipFilm.TREI_D ? "3D" : "2D");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    alegeFormatul.setText(tipFilm == TipFilm.TREI_D ? "3D" : "2D");
                }
            });
            alegeFormatul.getItems().add(item);
        }
        MenuItem item = new MenuItem();
        item.setText("Toate formatele");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alegeFormatul.setText("Toate formatele");
            }
        });
        alegeFormatul.getItems().add(item);
    }
    public void alegeGenul() {
        List<String> genuri = genuri();
        for(String gen : genuri) {
            MenuItem item = new MenuItem();
            item.setText(gen);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    alegeGenul.setText(gen);
                }
            });
            alegeGenul.getItems().add(item);

        }
        MenuItem item = new MenuItem();
        item.setText("Toate genurile");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alegeGenul.setText("Toate genurile");
            }
        });
        alegeGenul.getItems().add(item);

    }
    private List<String> genuri () {
        List<String> genuri = new ArrayList<>();
        for(Film film : filmList) {
            for(String gen : film.getGenuri()) {
                boolean ok = true;
                for(String g : genuri) {
                    if(g.equals(gen)) {
                        ok = false;
                        break;
                    }
                }
                if(ok) {
                    genuri.add(gen);
                }
            }
        }
        return genuri;
    }
    public List<TipFilm> tipuriFilme() {
        List<TipFilm> tipFilmeDataX = new ArrayList<>();
        for (Program program : programsList) {
            boolean ok = true;
            for (TipFilm tipFilm : tipFilmeDataX) {
                if (tipFilm == program.getTipFilm()) {
                    ok = false;
                    break;
                }
            }
            if(ok) {
                tipFilmeDataX.add(program.getTipFilm());
            }
        }
        return tipFilmeDataX;
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
    private List<Film> getFilmsListOnDateX (LocalDate date) {
        List<Film> filme = new ArrayList<>();
        for(Program program : map.get(date)) {
            boolean ok = true;
            for(Film film : filme) {
                if (program.getFilm().getId() == film.getId()) {
                    ok = false;
                    break;
                }
            }
            if(ok)
                filme.add(program.getFilm());

        }
        return filme;
    }
    private List<Film> getFilmsListOnDateXAndGenY (LocalDate date, String tF) {
        List<Film> filme = new ArrayList<>();
        for(Program program : map.get(date)) {
            if(tF.equals("ALEGE FORMATUL") || tF.equals("Toate formatele")) {
                boolean ok = true;
                for (Film film : filme) {
                    if (program.getFilm().getId() == film.getId()) {
                        ok = false;
                        break;
                    }
                }
                if (ok)
                    filme.add(program.getFilm());
            } else if(tF.equals("2D")) {
                if(program.getTipFilm() == TipFilm.DOI_D) {
                    boolean ok = true;
                    for (Film film : filme) {
                        if (program.getFilm().getId() == film.getId()) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok)
                        filme.add(program.getFilm());
                }
            } else {
                if(program.getTipFilm() == TipFilm.TREI_D) {
                    boolean ok = true;
                    for (Film film : filme) {
                        if (program.getFilm().getId() == film.getId()) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok)
                        filme.add(program.getFilm());
                }
            }
        }
        return filme;
    }
    public Map<TipFilm, Integer> getTipFilmIntegerMap (Film film, List<Program> programs) {
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
    private void setOnActionItemCantitate (MenuItem menuItem, MenuButton menuButton) {
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuButton.setText(menuItem.getText());
            }
        });
    }
    private void setOnActionButonFilmDinProgram(Button button, Film film, Program program) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    setProgramAles(program);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
                            pretCopil.setText(String.valueOf(pret.getPret()) + " lei");
                        if(pret.getTip_bilet() == TipUtilizator.ELEV)
                            pretElev.setText(String.valueOf(pret.getPret()) + " lei");
                        if(pret.getTip_bilet() == TipUtilizator.STUDENT)
                            pretStudent.setText(String.valueOf(pret.getPret()) + " lei");
                        if(pret.getTip_bilet() == TipUtilizator.ADULT)
                            pretAdult.setText(String.valueOf(pret.getPret()) + " lei");
                        if(pret.getTip_bilet() == TipUtilizator.PENSIONAR)
                            pretPensionar.setText(String.valueOf(pret.getPret()) + " lei");
                    }
                }
                for(int i = 1; i <= 15; i++) {
                    MenuItem item = new MenuItem();
                    item.setText(String.valueOf(i));
                    cantitateCopil.getItems().add(item);
                    setOnActionItemCantitate(item, cantitateCopil);
                    MenuItem item2 = new MenuItem();
                    item2.setText(String.valueOf(i));
                    cantitateElev.getItems().add(item2);
                    setOnActionItemCantitate(item2, cantitateElev);
                    MenuItem item3 = new MenuItem();
                    item3.setText(String.valueOf(i));
                    cantitateStudent.getItems().add(item3);
                    setOnActionItemCantitate(item3, cantitateStudent);
                    MenuItem item4 = new MenuItem();
                    item4.setText(String.valueOf(i));
                    cantitateAdult.getItems().add(item4);
                    setOnActionItemCantitate(item4, cantitateAdult);
                    MenuItem item5 = new MenuItem();
                    item5.setText(String.valueOf(i));
                    cantitatePensionar.getItems().add(item5);
                    setOnActionItemCantitate(item5, cantitatePensionar);
                }
            }

        });
    }
    public void generateGrid(LocalDate date) {
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
            titlu.setPrefWidth(600);
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
            VBox panouTipuriFilme = new VBox();
            for (Map.Entry<TipFilm, Integer> tipFilm : getTipFilmIntegerMap(film, map.get(date)).entrySet()) {
                if(tipFilm.getValue() != 0) {
                    VBox panouTip = new VBox();
                    Label tip = new Label(tipFilm.getKey() == TipFilm.DOI_D ? "2D" : "3D");
                    tip.setPadding(new Insets(10));
                    tip.setFont(Font.font("System", FontWeight.BOLD, 12));
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
                                if(!username.getText().equals("")) {
                                    setOnActionButonFilmDinProgram(button, film, program);
                                }

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
    private PreparedStatement prepare;
    public void schimbareDetaliiContSetDefaultText (Utilizator utilizatorConectat) {
        // System.out.println(utilizatorConectat);
        detaliiContUsername.setPromptText(utilizatorConectat.getUsername());
        detaliiContEmail.setPromptText(utilizatorConectat.getEmail());
        detaliiContParola.setPromptText(utilizatorConectat.getParola());
        detaliiContNume.setPromptText(utilizatorConectat.getNume());
        detaliiContPrenume.setPromptText(utilizatorConectat.getPrenume());
        detaliiContNrTel.setPromptText(utilizatorConectat.getNrTel());

    }
    public void OnActionButonSalveazaDetaliiCont() {
        String sql = "UPDATE UTILIZATOR SET NUME = ?, PRENUME = ?, EMAIL = ?, PAROLA = ?, USERNAME = ?, NR_TEL = ? WHERE ID_UTILIZATOR = " + utilizatorConectat.getId();
        try (Statement stmt = conn.createStatement()) {
            prepare = conn.prepareStatement(sql);
            if(! detaliiContNume.getText().isEmpty()) {
                prepare.setString(1, detaliiContNume.getText());
            }
            else {
                prepare.setString(1, utilizatorConectat.getNume());
            }
            if(! detaliiContPrenume.getText().isEmpty()) {
                prepare.setString(2, detaliiContPrenume.getText());
            }
            else {
                prepare.setString(2, utilizatorConectat.getPrenume());
            }
            if(! detaliiContEmail.getText().isEmpty()) {
                prepare.setString(3, detaliiContEmail.getText());
            }
            else {
                prepare.setString(3, utilizatorConectat.getEmail());
            }
            if(! detaliiContParola.getText().isEmpty()) {
                prepare.setString(4, detaliiContParola.getText());
            }
            else {
                prepare.setString(4, utilizatorConectat.getParola());
            }
            if(! detaliiContUsername.getText().isEmpty()) {
                prepare.setString(5, detaliiContUsername.getText());
            }
            else {
                prepare.setString(5, utilizatorConectat.getUsername());
            }
            if(! detaliiContNrTel.getText().isEmpty()) {
                prepare.setString(6, detaliiContNrTel.getText());
            }
            else {
                prepare.setString(6, utilizatorConectat.getNrTel());
            }


            Alert alert;
            if(detaliiContParola.getText().length() < 8 && !detaliiContParola.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare editare detalii cont!");
                alert.setHeaderText(null);
                alert.setContentText("Parolă invalidă");
                alert.showAndWait();

            } else if (!detaliiContEmail.getText().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-z]+$") && ! detaliiContEmail.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare editare detalii cont!");
                alert.setHeaderText(null);
                alert.setContentText("Email invalid");
                alert.showAndWait();
            } else if (detaliiContNrTel.getText().length() != 10 && ! detaliiContNrTel.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare editare detalii cont!");
                alert.setHeaderText(null);
                alert.setContentText("Număr de telefon invalid");
                alert.showAndWait();
            } else {
                prepare.execute();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mesaj de informare");
                alert.setContentText("Actualizare detalii cont facută cu succes!");
                alert.showAndWait();
                if(! detaliiContNume.getText().isEmpty()) {
                    utilizatorConectat.setNume(detaliiContNume.getText());
                }
                if(! detaliiContPrenume.getText().isEmpty()) {
                    utilizatorConectat.setPrenume(detaliiContPrenume.getText());
                }
                if(! detaliiContEmail.getText().isEmpty()) {
                    utilizatorConectat.setEmail(detaliiContEmail.getText());
                }
                if(! detaliiContParola.getText().isEmpty()) {
                    utilizatorConectat.setParola(detaliiContParola.getText());
                }
                if(! detaliiContNrTel.getText().isEmpty()) {
                    utilizatorConectat.setNrTel(detaliiContNrTel.getText());
                }
                if(! detaliiContUsername.getText().isEmpty()) {
                    utilizatorConectat.setUsername(detaliiContUsername.getText());
                }
                detaliiContNume.setText("");
                detaliiContPrenume.setText("");
                detaliiContEmail.setText("");
                detaliiContParola.setText("");
                detaliiContNrTel.setText("");
                detaliiContUsername.setText("");
                schimbareDetaliiContSetDefaultText(utilizatorConectat);
            }


        }catch (Exception e) {
            e.printStackTrace();

        }
    }
    public void finalizareComanda() {
        Alert alert;
        if(!radioButton.isSelected() || dataExpirare.getText().isEmpty() || numarCard.getText().isEmpty() || CVV2.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare completare formular");
            alert.setHeaderText(null);
            alert.setContentText("Vă rugăm completați toate câmpurile obligatorii!");
            alert.showAndWait();
        } else {
            boolean ok = true;
            if (!numarCard.getText().matches("^[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}$")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare completare formular");
                alert.setHeaderText(null);
                alert.setContentText("Format 'Numar card' invalid!");
                alert.showAndWait();
                ok = false;
            }
            else if (!dataExpirare.getText().matches("^[0-3][0-9]/[0-3][0-9]$")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare completare formular");
                alert.setHeaderText(null);
                alert.setContentText("Format 'Data expirare' invalid!");
                alert.showAndWait();
                ok = false;
            }
            else if (!CVV2.getText().matches("^[0-9][0-9][0-9]$")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Eroare completare formular");
                alert.setHeaderText(null);
                alert.setContentText("Format 'CVV2/CVC2' invalid!");
                alert.showAndWait();
                ok = false;
            }
            if (ok == true) {
                resetCantitate();
                formularPlata.setVisible(false);
                programFilme.setVisible(true);
            }
        }
    }
}
