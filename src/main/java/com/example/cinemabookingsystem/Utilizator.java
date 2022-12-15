package com.example.cinemabookingsystem;

public class Utilizator implements IsAdmin {
    private int id;
    private String username;
    private String emial;
    private String parola;
    private boolean isAdmin;

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    public Utilizator() {
    }

    public Utilizator(int id, String username, String emial, String parola, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.emial = emial;
        this.parola = parola;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "Utilizator:" +
                " id = " + id +
                " username = " + username +
                " emial = " + emial +
                " parola = " + parola +
                " isAdmin = " + isAdmin;
    }
}
