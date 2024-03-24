package com.example.projectmain.Refactoring.Command;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Post;

import java.util.List;

public abstract class Command{
    protected Post post;
    protected List<Post> postList;
    protected RecyclerView.Adapter adapter;
    protected  int position;

    public Command(Post post, List<Post> postList, RecyclerView.Adapter adapter, int position) {
        this.post = post;
        this.postList = postList;
        this.adapter = adapter;
        this.position = position;
    }
    public abstract void execute();
}
