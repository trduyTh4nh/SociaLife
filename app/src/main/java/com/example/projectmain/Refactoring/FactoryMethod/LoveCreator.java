package com.example.projectmain.Refactoring.FactoryMethod;

public class LoveCreator extends ReactionCreator{
    @Override
    public Reaction createReaction(int time, String idPost, String idUser, int id) {
        return new Love(time, idPost, idUser, id);
    }
}
