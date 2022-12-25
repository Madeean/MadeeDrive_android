package com.madeean.madeedrive.model;

public class ModelIsiData {
    int id;
    int user_id;
    String judul;
    String sinopsis;
    String foto_buku;
    int publik;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getFoto_buku() {
        return foto_buku;
    }

    public void setFoto_buku(String foto_buku) {
        this.foto_buku = foto_buku;
    }

    public int getPublik() {
        return publik;
    }

    public void setPublik(int publik) {
        this.publik = publik;
    }
}
