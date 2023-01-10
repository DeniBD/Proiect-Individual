package com.example.cinemabookingsystem.classes;

import com.example.cinemabookingsystem.classes.IsAdmin;

public class Utilizator implements IsAdmin {
    private int id;
    private String nume;

    private String prenume;

    private String nrTel;
    private String username;
    private String email;
    private String parola;
    private boolean isAdmin;


    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    public Utilizator() {
    }

    public Utilizator(int id, String nume, String prenume, String nrTel, String username, String email, String parola, boolean isAdmin) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.nrTel = nrTel;
        this.username = username;
        this.email = email;
        this.parola = parola;
        this.isAdmin = isAdmin;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNrTel() {
        return nrTel;
    }

    public void setNrTel(String nrTel) {
        this.nrTel = nrTel;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String emial) {
        this.email = emial;
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
                " nume = " + nume +
                " prenume = " + prenume +
                " nrTel = " + nrTel +
                " username = " + username +
                " email = " + email +
                " parola = " + parola +
                " isAdmin = " + isAdmin;
    }
}

