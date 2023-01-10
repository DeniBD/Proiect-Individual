package com.example.cinemabookingsystem.classes;

import java.util.Arrays;
import java.util.Map;

public class Rezervare {
    private int idUtilizator;
    private int idProgram;
    private int nrBilete;
    private String[] locuriOcupate;

    private Map<TipUtilizator, Integer> map;

    public Rezervare() {
    }

    public Rezervare(int idUtilizator, int idProgram, int nrBilete, String[] locuriOcupate, TipUtilizator tipUtilizator, Map<TipUtilizator, Integer> map) {
        this.idUtilizator = idUtilizator;
        this.idProgram = idProgram;
        this.nrBilete = nrBilete;
        this.locuriOcupate = locuriOcupate;
        this.map = map;
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

    public int getNrBilete() {
        return nrBilete;
    }

    public void setNrBilete(int nrBilete) {
        this.nrBilete = nrBilete;
    }

    public String[] getLocuriOcupate() {
        return locuriOcupate;
    }

    public void setLocuriOcupate(String[] locuriOcupate) {
        this.locuriOcupate = locuriOcupate;
    }

    public Map<TipUtilizator, Integer> getMap() {
        return map;
    }

    public void setMap(Map<TipUtilizator, Integer> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Rezervare:" +
                " idUtilizator = " + idUtilizator +
                " idProgram = " + idProgram +
                "locuri ocupate = " + Arrays.toString(locuriOcupate) +
                " tipuri bilet/cantitate = " + map;
    }
}
