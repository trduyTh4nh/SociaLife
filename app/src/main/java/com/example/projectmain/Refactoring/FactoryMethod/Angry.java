package com.example.projectmain.Refactoring.FactoryMethod;

public class Angry extends Reaction{
    public Angry(int time, String idPost, String idUser, int id) {
        super(time, idPost, idUser, id);
    }

    @Override
    public String getReaction() {
        return "😡";
    }
}
