package com.example.projectmain.Model;

public class NotifClass {
    private String name;
    private String message;
    private int hour;
    private String img;

    public String getCurTime() {
        return curTime;
    }

    public void setCurTime(String curTime) {
        this.curTime = curTime;
    }

    private String curTime;
    public NotifClass(String n, String i, String curTime, String avatar){
        name = n;
        message = i;
        this.curTime = curTime;
        this.img = avatar;
    }
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
