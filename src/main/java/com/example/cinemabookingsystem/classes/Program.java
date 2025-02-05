package com.example.cinemabookingsystem.classes;

import java.time.LocalDate;
import java.util.Objects;

public class Program {
    private int id_program;
    private Film film;
    private LocalDate data;
    private int oraInceput;
    private int minutInceput;
    private TipFilm tipFilm;
    private int nrBileteDisponibile;

    private SalaFilm salaFilm;

    public Program() {
    }

    public Program(int id_program, Film film, LocalDate data, int oraInceput, int minutInceput, TipFilm tipFilm, int nrBileteDisponibile, SalaFilm salaFilm) {
        this.id_program = id_program;
        this.film = film;
        this.data = data;
        this.oraInceput = oraInceput;
        this.minutInceput = minutInceput;
        this.tipFilm = tipFilm;
        this.nrBileteDisponibile = nrBileteDisponibile;
        this.salaFilm = salaFilm;
    }

    public int getId() {
        return id_program;
    }

    public void setId(int id_program) {
        this.id_program = id_program;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
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

    public int getNrBileteDisponibile() {
        return nrBileteDisponibile;
    }

    public void setNrBileteDisponibile(int nrBileteDisponibile) {
        this.nrBileteDisponibile = nrBileteDisponibile;
    }
    public SalaFilm getSalaFilm() {
        return salaFilm;
    }

    public void setSalaFilm(SalaFilm salaFilm) {
        this.salaFilm = salaFilm;
    }

    @Override
    public String toString() {
        return "Program:" +
                " id = " + id_program +
                " film = " + film +
                " data = " + data +
                " oraInceput = " + oraInceput +
                " minutInceput = " + minutInceput +
                " tipFilm = " + tipFilm + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return id_program == program.id_program && oraInceput == program.oraInceput && minutInceput == program.minutInceput && nrBileteDisponibile == program.nrBileteDisponibile && Objects.equals(film, program.film) && Objects.equals(data, program.data) && tipFilm == program.tipFilm && Objects.equals(salaFilm, program.salaFilm);
    }
}
