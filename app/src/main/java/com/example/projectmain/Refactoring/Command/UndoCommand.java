package com.example.projectmain.Refactoring.Command;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Post;

import java.util.ArrayList;
import java.util.List;

public class UndoCommand extends Command {
    private List<Post> undoPosts;

    public UndoCommand(Post post, List<Post> postList, RecyclerView.Adapter adapter, int position) {
        super(post, postList, adapter, position);
        this.undoPosts = new ArrayList<>();
    }

    public void addPostToUndo(Post post_) {
        if (post != null) {
            undoPosts.add(post_);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void execute() {
       postList.add(post);
       adapter.notifyDataSetChanged();
    }
}
