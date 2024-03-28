package com.example.projectmain.Refactoring.State;

import android.content.Context;

import com.example.projectmain.Database.DB;

public class EditedPostState implements IPostState{
    private PostContext context;

    private DB db;
    Context currentContext;
    public EditedPostState(Context currentContext) {
        this.currentContext = currentContext;
    }
    public PostContext getContext() {
        return context;
    }
    public void setContext(PostContext context) {
        this.context = context;
    }
    @Override
    public void editState(int post) {
        db = new DB(currentContext);
        db.updateStateEdit(post);
    }
}
