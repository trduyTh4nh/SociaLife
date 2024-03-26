package com.example.projectmain.Refactoring.Command;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Post;

import java.util.List;

public class DeleteCommand extends Command {
    private UndoCommand undoCommand;

    public DeleteCommand(Post post, List<Post> postList, RecyclerView.Adapter adapter, int position) {
        super(post, postList, adapter, position);
        undoCommand = new UndoCommand(post, postList, adapter, position);
       // undoCommand.addPostToUndo(undoCommand);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void execute() {
        // Thêm bài viết vào danh sách undo trước khi xóa
        postList.remove(post);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, postList.size());
    }
}

