package com.example.projectmain.Refactoring.FactoryMethod;

public abstract class Reaction {
    public Reaction(int time, String idPost, String idUser, int id) {
        this.time = time;
        this.idPost = idPost;
        this.id = id;
        this.idUser = idUser;
    }
    protected int id = -1;
    protected int time;
    protected String idPost;
    protected String idUser;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public abstract String getReaction();
    public void removeFromPost(){

    };
    public void incrementReaction(int time){
        this.time += time;
    };

}
