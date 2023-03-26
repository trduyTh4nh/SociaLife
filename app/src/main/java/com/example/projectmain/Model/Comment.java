package com.example.projectmain.Model;

public class Comment {

    private String commenter;
    private String commentContent;
    private int time;
    private int likes;
    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
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


    public Comment(String commenter, String commentContent, int time, int likes) {
        this.commenter = commenter;
        this.commentContent = commentContent;
        this.time = time;
        this.likes = likes;
    }




}
