package com.example.projectmain.Model;

import java.util.Date;

public class Post_up {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private int id;
    private int user;
    private String content;
    private byte[] img;
    private  int like_count;
    private  int comment_count;
    private int share_count;
    private Date date;



    public Post_up(int id, int user, String content, byte[] img, int like_count, int comment_count, int share_count, Date date) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.img = img;
        this.like_count = like_count;
        this.comment_count = comment_count;
        this.share_count = share_count;
        this.date = date;
    }
    

}
