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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

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

    private List<String> genuriSelectate = new ArrayList<>();


    @FXML
    private Label genuriCurente;
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
    @FXML
    private AnchorPane editareProgram;
    @FXML
    private DatePicker calendar;
    @FXML
    private MenuButton alegeGenul;
    @FXML
    private MenuButton alegeFormatul;
    @FXML
    private ScrollPane scrollPrograme;
    @FXML
    private ScrollPane scrollFilme;

    public MeniuAdminController() throws SQLException {

    }

    public DatePicker getCalendar() {
        return calendar;
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
            //adaugaPrograme.setVisible(false);
            editareStergerePrograme.setVisible(false);
            editareFilm.setVisible(false);
            editareProgram.setVisible(false);
            editareStergereFilme.setVisible(true);
        } else if (event.getSource() == butonProgrameMeniuAdmin) {
            alegeFormatul();
            alegeGenul();
            generateGridPrograme(LocalDate.now());
            adaugaFilme.setVisible(false);
            //adaugaPrograme.setVisible(false);
            editareStergereFilme.setVisible(false);
            editareProgram.setVisible(false);
            editareStergerePrograme.setVisible(true);
        } else if (event.getSource() == butonAdaugaFilme) {
            //adaugaPrograme.setVisible(false);
            editareStergereFilme.setVisible(false);
            editareStergerePrograme.setVisible(false);
            editareProgram.setVisible(false);
            adaugaFilme.setVisible(true);
        } else if (event.getSource() == butonAdaugaPrograme) {
            editareStergereFilme.setVisible(false);
            editareStergerePrograme.setVisible(false);
            editareProgram.setVisible(false);
            adaugaFilme.setVisible(false);
            //adaugaPrograme.setVisible(true);
        }
    }
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
        editareTitluFilm.setPromptText(film.getTitlu());
        editareDurataFilm.setPromptText(String.valueOf(film.getDurata()));
        genuriSelectate = new ArrayList<>();
        for(String gen : film.getGenuri()) {
            genuriSelectate.add(gen);
        }
        setLabelGenuri();
    }

    public void setLabelGenuri() {
        String genuri = "";
        for(String gen : genuriSelectate) {
            genuri += gen + ", ";
        }
        genuri = genuri.substring(0, genuri.lastIndexOf(','));
        genuriCurente.setText(genuri);
    }
    public void editareFilm(Film film) {
        String sql1 = "UPDATE FILM SET TITLU = ?, DURATA = ? WHERE ID_FILM = " + film.getId();
        String sql2 = "DELETE FROM GEN WHERE ID_FILM = " + film.getId();
        String sql3 = "INSERT INTO GEN (ID_FILM, GEN) VALUES ";
        try (PreparedStatement pstmt1 = conn.prepareStatement(sql1); PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            if(!editareTitluFilm.getText().isEmpty()) {
                film.setTitlu(editareTitluFilm.getText());
                pstmt1.setString(1, editareTitluFilm.getText());
            } else {
                pstmt1.setString(1, film.getTitlu());
            }
            if(!editareDurataFilm.getText().isEmpty()) {
                film.setDurata(Integer.parseInt(editareDurataFilm.getText()));
                pstmt1.setString(2, editareDurataFilm.getText());
            } else {
                pstmt1.setInt(2, film.getDurata());
            }
            pstmt1.execute();
            pstmt2.execute();

            if(genuriSelectate.size() == 0) {
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mesaj de informare");
                alert.setContentText("Vă rugam introduceți cel putin un gen pentru film! ");
                alert.showAndWait();
            } else {
                //////////////////////////////////////////////////////////////////////////
                for(String gen : genuriSelectate) {
                    sql3 += "(" + film.getId() + ", '" + gen + "'), ";
                }
                sql3 = sql3.substring(0, sql3.lastIndexOf(','));
                PreparedStatement pstmt3 = conn.prepareStatement(sql3);
                pstmt3.execute();
                film.setGenuri(genuriSelectate.toArray(new String[0]));
            }

            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mesaj de informare");
            alert.setContentText("Editare detalii film facută cu succes!");
            alert.showAndWait();
            editareTitluFilm.setText("");
            editareDurataFilm.setText("");
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
                schimbareDetaliiFilm(film);
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
            //  Set Pressed
            if(genuriSelectate.contains(gen)) {
                menuItem.setStyle("-fx-background-color: grey");
            }
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(genuriSelectate.contains(gen)) {
                        //  Set Not Pressed (Default)
                        menuItem.setStyle("-fx-background-color: white");
                        genuriSelectate.remove(gen);
                        setLabelGenuri();
                    } else {
                        //  Set Pressed
                        menuItem.setStyle("-fx-background-color: grey");
                        genuriSelectate.add(gen);
                        setLabelGenuri();
                    }
                }
            });
            editareGenuriFilm.getItems().add(menuItem);
        }
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
    public void generateGridFilme() throws SQLException, IOException {
        //filmList = filmList(conn);

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
        scrollFilme.setContent(banners);
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
    private void setOnActionButonFilmDinProgram(Button button, Film film, Program program) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                editareStergerePrograme.setVisible(false);
                editareProgram.setVisible(true);

            }
        });
    }
    public void generateGridPrograme(LocalDate date) {

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
                                setOnActionButonFilmDinProgram(button, film, program);
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
        scrollPrograme.setContent(banners);
    }
    public LocalDate getDate() {
        LocalDate selectedDate = calendar.getValue();
        //System.out.println(selectedDate);
        if(selectedDate == null)
            return LocalDate.now();
        return selectedDate;
        //String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
    public void gridFiltrat(LocalDate date, String tF, String g) {

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
                                setOnActionButonFilmDinProgram(button, film, program);
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
        scrollPrograme.setContent(banners);
    }
    public List<LocalDate> dateProgram() {
        List<LocalDate> dateProgram = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Program>> data : map.entrySet()) {
            dateProgram.add(data.getKey());
        }
        return dateProgram;
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
}