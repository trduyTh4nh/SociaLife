package com.example.projectmain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Comment;
import com.example.projectmain.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    ArrayList<Comment> cmtList;
    Context context;

    private TextView tvUserName, tvContent, tvTime,tvLikes;

    public CommentAdapter(ArrayList<Comment> cmtList, Context context) {
        this.cmtList = cmtList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = cmtList.get(position);
        tvUserName.setText(comment.getCommenter());
        tvContent.setText(comment.getCommentContent());

        int time = comment.getTime();

        if(time > 24)
            tvTime.setText(time / 24 + " Ngày trước");
        else
            tvTime.setText(time / 24 + " Giờ");
        int like = comment.getLikes();

        if(time > 1000)
            tvLikes.setText(like / 1000 + "K");
        else
            tvLikes.setText(String.valueOf(comment.getLikes()));
    }

    @Override
    public int getItemCount() {
        return cmtList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View view) {
            super(view);

            tvUserName = view.findViewById(R.id.tvUser);
            tvContent = view.findViewById(R.id.tvContent);
            tvTime = view.findViewById(R.id.tvTimeCmt);
            tvLikes = view.findViewById(R.id.tvLikeCount);
        }
    }

}
