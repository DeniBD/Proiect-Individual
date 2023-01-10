package com.example.cinemabookingsystem;
import com.example.cinemabookingsystem.classes.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;


public class DatabaseConnection {

    public static Connection getConnection() {
        String databaseName = "cinema_booking_system_db";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            return databaseLink;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Pret> pretList (Connection con) throws SQLException {
        String query = "select TIP_FILM, TIP_BILET, PRET from PRET";
        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Pret> preturi = new ArrayList<>();

            while (resultSet.next()) {
                String tipFilm = resultSet.getString("TIP_FILM");
                String tipBilet = resultSet.getString("TIP_BILET");
                double pret = resultSet.getDouble("PRET");
                TipUtilizator tipUtilizator = null;
                if(tipBilet.equals("copil"))
                    tipUtilizator = TipUtilizator.COPIL;
                if(tipBilet.equals("elev"))
                    tipUtilizator = TipUtilizator.ELEV;
                if(tipBilet.equals("student"))
                    tipUtilizator = TipUtilizator.STUDENT;
                if(tipBilet.equals("adult"))
                    tipUtilizator = TipUtilizator.ADULT;
                if(tipBilet.equals("pensionar"))
                    tipUtilizator = TipUtilizator.PENSIONAR;
                preturi.add(new Pret((tipFilm.equals("2D") ? TipFilm.DOI_D : TipFilm.TREI_D), tipUtilizator, pret));
            }
            //System.out.println(preturi);
            return preturi;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Utilizator utilizatorConectat(Connection con, String username, String parola) throws SQLException {
        String query = "SELECT * FROM UTILIZATOR WHERE USERNAME = \'" + username + "\' AND PAROLA = \'" + parola + "\'";
        try (Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            int id = resultSet.getInt("ID_UTILIZATOR");
            String nume = resultSet.getString("NUME");
            String prenume = resultSet.getString("PRENUME");
            int boolAdmin = resultSet.getInt("IS_ADMIN");
            String email = resultSet.getString("EMAIL");
            String nrTel = resultSet.getString("NR_TEL");
            return new Utilizator(id, nume, prenume, nrTel, username, email, parola, (boolAdmin == 1 ? true : false));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Film> filmList (Connection con) throws SQLException {
        String query1 = "select ID_FILM, TITLU, SRC_POZA, DURATA from FILM";
        try (Statement statement1 = con.createStatement()) {
            ResultSet resultSet1 = statement1.executeQuery(query1);
            List<Film> filmList = new ArrayList<>();
            while (resultSet1.next()) {
                int id = resultSet1.getInt("ID_FILM");
                String titlu = resultSet1.getString("TITLU");
                String srcImg = resultSet1.getString("SRC_POZA");
                int durata = resultSet1.getInt("DURATA");

                List<String> genuri = new ArrayList();

                String query2 = "select GEN from GEN WHERE ID_FILM = " + id;
                try (Statement statement2 = con.createStatement()){
                    ResultSet resultSet2 = statement2.executeQuery(query2);
                    while (resultSet2.next()) {
                        String gen = resultSet2.getString("GEN");
                        genuri.add(gen);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //System.out.println(genuri.get(0));
                String[] g = genuri.toArray(new String[0]);
                filmList.add(new Film(id, "images/" + srcImg, titlu, durata, g));
            }
            //System.out.println(filmList);
            return filmList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<LocalDate> returnCalendarProgram(int id_program, Connection con) {
        String query1 = "select DATA from CALENDAR_PROGRAM where ID_PROGRAM = " + id_program;
        try (Statement statement1 = con.createStatement()) {
            ResultSet resultSet1 = statement1.executeQuery(query1);
            List<LocalDate> datesList = new ArrayList<>();
            while (resultSet1.next()) {
                LocalDate date = resultSet1.getDate("DATA").toLocalDate();
                datesList.add(date);
            }
            return datesList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static  List<Program> programsList (Connection con) throws SQLException {
        String query1 = "select DISTINCT ID_PROGRAM, ID_FILM, ORA_INCEPUT, MINUT_INCEPUT, TIP_FILM, NR_BILETE_DISPONIBILE, LOCURI_OCUPATE from PROGRAM";
        try (Statement statement1 = con.createStatement()) {
            ResultSet resultSet1 = statement1.executeQuery(query1);
            List<Program> programsList = new ArrayList<>();
            while (resultSet1.next()) {
                int idProgram = resultSet1.getInt("ID_PROGRAM");
                int idFilm = resultSet1.getInt("ID_FILM");
                String query2 = "select * from FILM where ID_FILM = " + idFilm;
                Film film = new Film();
                try (Statement statement2 = con.createStatement()) {
                    ResultSet resultSet2 = statement2.executeQuery(query2);
                    resultSet2.next();
                    int id_film = resultSet2.getInt("ID_FILM");
                    String titlu = resultSet2.getString("TITLU");
                    String srcImg = resultSet2.getString("SRC_POZA");
                    int durata = resultSet2.getInt("DURATA");
                    film.setId(id_film);
                    film.setTitlu(titlu);
                    film.setImgSrc("images/" + srcImg);
                    film.setDurata(durata);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String query3 = "select GEN from GEN WHERE ID_FILM = " + idFilm;
                List<String> genuri = new ArrayList();
                try (Statement statement3 = con.createStatement()) {
                    ResultSet resultSet3 = statement3.executeQuery(query3);
                    while (resultSet3.next()) {
                        String gen = resultSet3.getString("GEN");
                        genuri.add(gen);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String[] g = genuri.toArray(new String[0]);
                film.setGenuri(g);
            int oraInceput = resultSet1.getInt("ORA_INCEPUT");
            int minutInceput = resultSet1.getInt("MINUT_INCEPUT");
            String tipFilm = resultSet1.getString("TIP_FILM");
            TipFilm tF = tipFilm.equals("3D") ? TipFilm.TREI_D : TipFilm.DOI_D;
            int nrBileteDisponibile = resultSet1.getInt("NR_BILETE_DISPONIBILE");

            String locuriOcupate = resultSet1.getString("LOCURI_OCUPATE");
            String[] locuri = locuriOcupate.split(", ", 0);

            SalaFilm salaFilm = new SalaFilm();
            Class c = salaFilm.getClass();
            for(int i = 0; i < locuri.length; i++) {
                if(locuri[i].length() != 0) {
                    String tmp = "setR" + locuri[i].substring(1);
                    // System.out.println(tmp);
                    Method m = c.getDeclaredMethod(tmp, boolean.class);
                    m.invoke(salaFilm, true);
                }
            }
            // System.out.println(salaFilm);

            List<LocalDate> dates = returnCalendarProgram(idProgram, getConnection());
            for (LocalDate d : dates) {
                programsList.add(new Program(idProgram, film, d, oraInceput, minutInceput, tF, nrBileteDisponibile, salaFilm));
            }

            }
            //System.out.println(programsList);
            return programsList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("kjdvnijsdfvnd");
        return null;
    }
}
