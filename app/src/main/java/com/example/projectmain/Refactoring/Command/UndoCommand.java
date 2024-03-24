package com.example.projectmain.Refactoring.Command;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Post;

import java.util.ArrayList;
import java.util.List;

public class UndoCommand extends Command {
    private List<Post> undo_posts;
    private int undo_position;

    public UndoCommand(Post post, List<Post> postList, RecyclerView.Adapter adapter, int position) {
        super(post, postList, adapter, position);
        this.undo_posts = new ArrayList<>();
        this.undo_position = position; 
    }

    public void addPostToUndo(Post post) {
        if (post != null) {
            undo_posts.add(post);
        }
    }

    @Override
    public void execute() {
        for (int i = 0; i < undo_posts.size(); i++) {
            postList.add(undo_position + i, undo_posts.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}
