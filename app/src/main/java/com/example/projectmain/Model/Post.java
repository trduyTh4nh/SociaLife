package com.example.projectmain.Model;

import com.example.projectmain.Refactoring.Mememto.PostMemento;

public class  Post {
    public Boolean getShare() {
        return isShare;
    }

    public void setShare(Boolean share) {
        isShare = share;
    }

    Boolean isShare = false;
    public Post() {
    }

//    int avatar;
//    int imgPost;
//    String username;
//    String name;
//    String number_like;
//    String content;
//    String time;
public Post(int id, int iduser, String avatar, String imgPost, String username, String name, String number_like, String content, String time, boolean isShare) {
    this.id = id;
    this.iduser = iduser;
    this.avatar = avatar;
    this.imgPost = imgPost;
    this.username = username;
    this.name = name;
    this.number_like = number_like;
    this.content = content;
    this.time = time;
    this.isShare = isShare;
}
    public Post(int id, int iduser, String avatar, String imgPost, String username, String name, String number_like, String content, String time) {
        this.id = id;
        this.iduser = iduser;
        this.avatar = avatar;
        this.imgPost = imgPost;
        this.username = username;
        this.name = name;
        this.number_like = number_like;
        this.content = content;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    int iduser;
    String avatar;
    String imgPost;
    String username;
    String name;
    String number_like;
    String content;
    String time;

    Integer statePost;

    String postContent;

    Post sharedPost;


    public Integer getStatePost() {
        return statePost;
    }

    public void setStatePost(Integer statePost) {
        this.statePost = statePost;
    }

    public Post(int iduser, String avatar, String imgPost, String username, String name, String number_like, String content, String time) {
        this.avatar = avatar;
        this.iduser = iduser;
        this.imgPost = imgPost;
        this.username = username;
        this.name = name;
        this.number_like = number_like;
        this.content = content;
        this.time = time;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }


    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImgPost() {
        return imgPost;
    }

    public void setImgPost(String imgPost) {
        this.imgPost = imgPost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber_like() {
        return number_like;
    }

    public void setNumber_like(String number_like) {
        this.number_like = number_like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Post getSharedPost() {
        return sharedPost;
    }
    public void setSharedPost(Post sharedPost) {
        this.sharedPost = sharedPost;
    }

    public Post(int id, String avatar, Post sharedPost, String username, String name, String number_like, String content, String time) {
        this.id = id;
        this.avatar = avatar;
        this.sharedPost = sharedPost;
        this.username = username;
        this.name = name;
        this.number_like = number_like;
        this.content = content;
        this.time = time;
    }
    public Post(int id, int idUser, String avatar, Post sharedPost, String username, String name, String number_like, String content, String time) {
        this.iduser = idUser;
        this.id = id;
        this.avatar = avatar;
        this.sharedPost = sharedPost;
        this.username = username;
        this.name = name;
        this.number_like = number_like;
        this.content = content;
        this.time = time;
    }

    public PostMemento saveToMemento() {
        return new PostMemento(this);
    }

    public void restoreFromMemento(PostMemento memento) {
        this.id = memento.getState().id;
        this.iduser = memento.getState().iduser;
        this.avatar = memento.getState().avatar;
        this.imgPost = memento.getState().imgPost;
        this.username = memento.getState().username;
        this.name = memento.getState().name;
        this.number_like = memento.getState().number_like;
        this.content = memento.getState().content;
        this.time = memento.getState().time;
        this.isShare = memento.getState().isShare;
    }
}
