package com.madeean.madeedrive.model;

public class ModelIsiDataAuthLogin {
    private int id;
    private String name;
    private String email;
    private String password;
    private int konfirmasi;
    private int admin;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(int konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
