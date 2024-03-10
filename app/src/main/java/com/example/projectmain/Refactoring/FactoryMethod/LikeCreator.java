package com.example.projectmain.Refactoring.FactoryMethod;

public class LikeCreator extends ReactionCreator {
    @Override
    public Reaction createReaction(int time, String idPost, String idUser, int id) {
        return new Like(time, idPost, idUser, id);
    }
}
