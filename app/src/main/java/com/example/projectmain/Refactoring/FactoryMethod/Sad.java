package com.example.projectmain.Refactoring.FactoryMethod;

public class Sad extends Reaction {
    public Sad(int time, String idPost, String idUser, int id) {
        super(time, idPost, idUser, id);
    }
    @Override
    public String getReaction() {
        return "😢";
    }
    public static int getType() {
        return 2;
    }
}
