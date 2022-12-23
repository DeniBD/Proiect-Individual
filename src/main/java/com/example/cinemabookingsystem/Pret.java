package com.example.cinemabookingsystem;

public class Pret {
    private String tip_film;
    private String tip_bilet;
    private double pret;

    public Pret() {
    }

    public Pret(String tip_film, String tip_bilet, double pret) {
        this.tip_film = tip_film;
        this.tip_bilet = tip_bilet;
        this.pret = pret;
    }

    public String getTip_film() {
        return tip_film;
    }

    public void setTip_film(String tip_film) {
        this.tip_film = tip_film;
    }

    public String getTip_bilet() {
        return tip_bilet;
    }

    public void setTip_bilet(String tip_bilet) {
        this.tip_bilet = tip_bilet;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "tip_film = " + tip_film +
                " tip_bilet = " + tip_bilet +
                " pret=" + pret +
                "\n";
    }
}
