package com.example.projectmain.Class;

public class Commentclass{
    private String commenter;
    private String commentcontent;
    private int time;
    private int likes;

    public Commentclass(String commenter, String commentcontent, int time, int likes) {
        this.commenter = commenter;
        this.commentcontent = commentcontent;
        this.time = time;
        this.likes = likes;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
