package com.example.cinemabookingsystem;

import java.util.Arrays;

public class Rezervare {
    private int idUtilizator;
    private int idProgram;
    private int nrBilete;
    private int[] nrRand;
    private int[] nrLoc;
    private TipUtilizator tipUtilizator;

    public Rezervare() {
    }

    public Rezervare(int idUtilizator, int idProgram, int nrBilete, int[] nrRand, int[] nrLoc, TipUtilizator tipUtilizator) {
        this.idUtilizator = idUtilizator;
        this.idProgram = idProgram;
        this.nrBilete = nrBilete;
        this.nrRand = nrRand;
        this.nrLoc = nrLoc;
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

    public int[] getNrRand() {
        return nrRand;
    }

    public void setNrRand(int[] nrRand) {
        this.nrRand = nrRand;
    }

    public int[] getNrLoc() {
        return nrLoc;
    }

    public void setNrLoc(int[] nrLoc) {
        this.nrLoc = nrLoc;
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
                " nrRand = " + Arrays.toString(nrRand) +
                " nrLoc = " + Arrays.toString(nrLoc) +
                " tipUtilizator = " + tipUtilizator;
    }
}
