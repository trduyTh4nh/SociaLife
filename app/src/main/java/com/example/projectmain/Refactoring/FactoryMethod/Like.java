package com.example.projectmain.Refactoring.FactoryMethod;

public class Like extends Reaction {

    public Like(int time, String idPost, String idUser, int id) {
        super(time, idPost, idPost, id);
    }

    @Override
    public String getReaction() {
        return "👍";
    }
    public static int getType() {
        return 0;
    }
}
