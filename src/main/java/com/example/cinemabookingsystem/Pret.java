package com.example.cinemabookingsystem;

public class Pret {
    private TipFilm tip_film;
    private TipUtilizator tip_bilet;
    private double pret;

    public Pret() {
    }

    public Pret(TipFilm tip_film, TipUtilizator tip_bilet, double pret) {
        this.tip_film = tip_film;
        this.tip_bilet = tip_bilet;
        this.pret = pret;
    }

    public TipFilm getTip_film() {
        return tip_film;
    }

    public void setTip_film(TipFilm tip_film) {
        this.tip_film = tip_film;
    }

    public TipUtilizator getTip_bilet() {
        return tip_bilet;
    }

    public void setTip_bilet(TipUtilizator tip_bilet) {
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
