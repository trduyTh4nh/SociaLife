package com.example.projectmain.Refactoring.Command;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Post;

import java.util.List;

public class DeleteCommand extends Command{
    public DeleteCommand(Post post, List<Post> postList, RecyclerView.Adapter adapter, int position) {
        super(post, postList, adapter, position);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void execute() {
        UndoCommand undoCommand = new UndoCommand(post, postList, adapter, position);
        undoCommand.addPostToUndo(post);
        postList.remove(post);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, postList.size());
    }

}
