package com.example.projectmain.Model;

public class Account {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
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

    private int id;
    private int user;
    private int iduser;
    private String email;
    private String password;

    public Account(int id, int user, int iduser, String email, String password) {
        this.id = id;
        this.user = user;
        this.iduser = iduser;
        this.email = email;
        this.password = password;
    }



    public Account(){}
}
