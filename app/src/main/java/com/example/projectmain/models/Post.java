package com.example.projectmain.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private int id;
    private int iduser;
    private String content;
    private byte[] image;
    private int like_count;
    private int comment_count;
    private int share_count;
    private Date datetime;

    public Post(int id, int iduser, String content, byte[] image, int like_count, int comment_count, int share_count, Date datetime) {
        this.id = id;
        this.iduser = iduser;
        this.content = content;
        this.image = image;
        this.like_count = like_count;
        this.comment_count = comment_count;
        this.share_count = share_count;
        this.datetime = datetime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
