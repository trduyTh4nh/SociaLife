package com.example.projectmain.models;

public class Account {
    private int id;
    private int iduser;
    private String email;
    private String password;

    public Account(int id, int iduser, String email, String password) {
        this.id = id;
        this.iduser = iduser;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
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
}
