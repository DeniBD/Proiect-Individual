package com.example.cinemabookingsystem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

                preturi.add(new Pret(tipFilm, tipBilet, pret));
            }
            //System.out.println(preturi);
            return preturi;

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

                String query2 = "select GEN from FILM WHERE ID_FILM = " + id;
                try (Statement statement2 = con.createStatement()){
                    ResultSet resultSet2 = statement2.executeQuery(query2);
                    while (resultSet2.next()) {
                        String gen = resultSet2.getString("GEN");
                        genuri.add(gen);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String[] g = genuri.toArray(new String[0]);
                filmList.add(new Film(id, titlu, srcImg, durata, g));
            }
            System.out.println(filmList);
            return filmList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
