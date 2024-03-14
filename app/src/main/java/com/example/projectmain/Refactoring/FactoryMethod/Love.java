package com.example.projectmain.Refactoring.FactoryMethod;

public class Love extends Reaction{
    public Love(int time, String idPost, String idUser, int id) {
        super(time, idPost, idUser, id);
    }

    @Override
    public String getReaction() {
        return "❤️";
    }
}
