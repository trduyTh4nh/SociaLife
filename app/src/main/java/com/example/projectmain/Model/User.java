package com.example.projectmain.Model;

public class User {
    private int id;
    private String likeType;
    private String name;
    private String image; //con lạy ông nội
    private int post_count;
    private int following_count;
    private int follower_count;
    private String description;

    public User(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public User(int id, String likeType, String name, String description) {
        this.id = id;
        this.likeType = likeType;
        this.name = name;
        this.description = description;
    }

    public String getLikeType() {
        return likeType;
    }

    public void setLikeType(String likeType) {
        this.likeType = likeType;
    }

    public User() {
        this.id = id;
        this.name = name;
        this.image = image;
        this.post_count = post_count;
        this.following_count = following_count;
        this.follower_count = follower_count;
        this.description = description;
        //HẢ????
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPost_count() {
        return post_count;
    }

    public void setPost_count(int post_count) {
        this.post_count = post_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}