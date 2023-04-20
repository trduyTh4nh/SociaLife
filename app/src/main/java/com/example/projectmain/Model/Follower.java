package com.example.projectmain.Model;

public class Follower {
    String avatar;
    String name;

    String userName;
    boolean state;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }



    public Follower(String avatar, String name, String userName, boolean state) {
        this.avatar = avatar;
        this.name = name;
        this.userName = userName;
        this.state = state;
    }
}
