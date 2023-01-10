package com.example.cinemabookingsystem.classes;

import java.util.Arrays;

public class Film {
    private int id;
    private String ImgSrc;
    private String titlu;
    private int durata;
    private String[] genuri;

    public Film() {
    }

    public Film(int id, String ImgSrc, String titlu, int durata, String[] genuri) {
        this.id = id;
        this.ImgSrc = ImgSrc;
        this.titlu = titlu;
        this.durata = durata;
        this.genuri = genuri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String[] getGenuri() {
        return genuri;
    }

    public void setGenuri(String[] genuri) {
        this.genuri = genuri;
    }

    public String getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(String imgSrc) {
        ImgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "Film:" +
                " id = " + id +
                " src = " + ImgSrc +
                " nume = " + titlu +
                " durata = " + durata +
                " genuri = " + Arrays.toString(genuri);
    }
}
