package com.example.projectmain.Refactoring.State;

public class PostContext implements IPostState{
    private IPostState state;

    public PostContext(IPostState state) {
        this.state = state;
    }

    @Override
    public void editState(int post) {
        state.editState(post);
    }

    public void changeSate(IPostState newState){
        setState(newState);
    }


    public IPostState getState() {
        return state;
    }

    public void setState(IPostState state) {
        this.state = state;
    }

}
