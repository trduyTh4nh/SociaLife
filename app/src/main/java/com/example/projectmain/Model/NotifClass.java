package com.example.projectmain.Model;

public class NotifClass {
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private String name;
    private String message;
    private int hour;
    private String img;

    public NotifClass(int ID, String name, String message, String img, String curTime) {
        this.ID = ID;
        this.name = name;
        this.message = message;

        this.img = img;
        this.curTime = curTime;
    }

    public String getCurTime() {
        return curTime;
    }

    public void setCurTime(String curTime) {
        this.curTime = curTime;
    }

    private String curTime;
//    public NotifClass(String n, String i, String curTime, String avatar){
//        name = n;
//        message = i;
//        this.curTime = curTime;
//        this.img = avatar;
//    }
//    public NotifClass(String n, String m, int h, String i){
//        name = n;
//        message = m;
//        hour = h;
//        img = i;
//    }

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
