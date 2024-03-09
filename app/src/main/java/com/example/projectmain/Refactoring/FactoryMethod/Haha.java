package com.example.projectmain.Refactoring.FactoryMethod;

public class Haha extends Reaction {
    public Haha(int time, String idPost, String idUser, int id) {
        super(time, idPost, idPost, id);
    }
    @Override
    public String getReaction() {
        return "😂";
    }



    public static int getType() {
        return 1;
    }
}
