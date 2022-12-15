package com.example.cinemabookingsystem;

public class Program {
    private int id;
    private Film film;
    private Data data;
    private int oraInceput;
    private int minutInceput;
    private TipFilm tipFilm;

    public Program() {
    }

    public Program(int id, Film film, Data data, int oraInceput, int minutInceput, TipFilm tipFilm) {
        this.id = id;
        this.film = film;
        this.data = data;
        this.oraInceput = oraInceput;
        this.minutInceput = minutInceput;
        this.tipFilm = tipFilm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(int oraInceput) {
        this.oraInceput = oraInceput;
    }

    public int getMinutInceput() {
        return minutInceput;
    }

    public void setMinutInceput(int minutInceput) {
        this.minutInceput = minutInceput;
    }

    public TipFilm getTipFilm() {
        return tipFilm;
    }

    public void setTipFilm(TipFilm tipFilm) {
        this.tipFilm = tipFilm;
    }

    @Override
    public String toString() {
        return "Program:" +
                " id = " + id +
                " film = " + film +
                " data = " + data +
                " oraInceput = " + oraInceput +
                " minutInceput = " + minutInceput +
                " tipFilm = " + tipFilm;
    }
}
