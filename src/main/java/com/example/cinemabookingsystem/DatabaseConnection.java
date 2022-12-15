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

    public static List<Pret> viewTable(Connection con) throws SQLException {
        String query = "select TIP_FILM, TIP_BILET, PRET from PRET";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String tipFilm = rs.getString("TIP_FILM");
                String tipBilet = rs.getString("TIP_BILET");
                double pret = rs.getDouble("PRET");

                List<Pret> preturi = new ArrayList<>();
                preturi.add(new Pret(tipFilm, tipBilet, pret));

                return preturi;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
