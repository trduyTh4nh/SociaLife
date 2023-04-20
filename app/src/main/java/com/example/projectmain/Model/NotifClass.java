package com.example.projectmain.Model;

public class NotifClass {
    private String name;
    private String message;
    private int hour;
    private String img;
    public NotifClass(String n, String i){
        name = n;
        img = i;
    }
    public NotifClass(String n, String m, int h, String i){
        name = n;
        message = m;
        hour = h;
        img = i;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
