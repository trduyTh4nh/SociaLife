package com.example.projectmain.Refactoring.FactoryMethod;

public abstract class ReactionCreator {
    public abstract Reaction createReaction(int time, String idPost, String idUser, int id);
    public void reactToPost(int time, String idPost, String idUser, int id){
        Reaction reaction = createReaction(1, idPost, idUser, id);
        //TODO: Implement database và code cho việc react cho post.
    }
}
