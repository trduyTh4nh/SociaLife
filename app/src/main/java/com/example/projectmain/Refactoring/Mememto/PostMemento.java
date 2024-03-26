package com.example.projectmain.Refactoring.Mememto;

import com.example.projectmain.Model.Post;

public class PostMemento  {
    private final Post post;
    public PostMemento(Post post){
        this.post = post;
    }
    public Post getState(){
        return post;
    }
}
