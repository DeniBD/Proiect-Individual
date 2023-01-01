package com.example.cinemabookingsystem;

import java.util.Arrays;

public class Rezervare {
    private int idUtilizator;
    private int idProgram;
    private int nrBilete;

    private String[] locuriOcupate;
    private TipUtilizator tipUtilizator;

    public Rezervare() {
    }

    public Rezervare(int idUtilizator, int idProgram, int nrBilete, String[] locuriOcupate, TipUtilizator tipUtilizator) {
        this.idUtilizator = idUtilizator;
        this.idProgram = idProgram;
        this.nrBilete = nrBilete;
        this.locuriOcupate = locuriOcupate;
        this.tipUtilizator = tipUtilizator;
    }

    public int getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(int idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public int getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    public String[] getNrRand() {
        return locuriOcupate;
    }

    public void setNrRand(String[] locuriOcupate) {
        this.locuriOcupate = locuriOcupate;
    }

    public TipUtilizator getTipUtilizator() {
        return tipUtilizator;
    }

    public void setTipUtilizator(TipUtilizator tipUtilizator) {
        this.tipUtilizator = tipUtilizator;
    }

    public int getNrBilete() {
        return nrBilete;
    }

    public void setNrBilete(int nrBilete) {
        this.nrBilete = nrBilete;
    }

    @Override
    public String toString() {
        return "Rezervare:" +
                " idUtilizator = " + idUtilizator +
                " idProgram = " + idProgram +
                "locuri ocupate = " + Arrays.toString(locuriOcupate) +
                " tipUtilizator = " + tipUtilizator;
    }
}
