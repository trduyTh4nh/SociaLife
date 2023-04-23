package com.example.projectmain.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Post;
import com.example.projectmain.R;

import java.util.ArrayList;

public class TextPostAdapter extends RecyclerView.Adapter<TextPostAdapter.TPHolder> {
    Context c;
    ArrayList<Post> posts;
    Boolean isEmpty = false;
    public TextPostAdapter(Context c, ArrayList<Post> posts){
        this.c = c;
        this.posts = posts;
        if(posts.size() == 0){
            posts.add(new Post());
            isEmpty = true;
        }
    }
    @NonNull
    @Override
    public TPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(isEmpty){
            v = LayoutInflater.from(c).inflate(R.layout.error, parent, false);
            return new TPHolder(v);
        }
        v = LayoutInflater.from(c).inflate(R.layout.text_post, parent, false);
        return new TPHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TPHolder holder, int position) {
        if(isEmpty){
            holder.tvError.setText("Chưa có bài đăng văn bản");
            holder.tvErrorMsg.setText("Hãy đăng 1 bài đăng dưới dạng văn bản như là một trạng thái hoặc một đoạn văn.");
            return;
        }
        holder.tvContent.setText(posts.get(position).getContent());
        holder.tvLike.setText(posts.get(position).getNumber_like());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class TPHolder extends RecyclerView.ViewHolder{
        private TextView tvContent, tvLike, tvError, tvErrorMsg;

        public TPHolder(@NonNull View itemView) {
            super(itemView);
            tvErrorMsg = itemView.findViewById(R.id.tvErrorMsg);
            tvError = itemView.findViewById(R.id.tvError);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvLike = itemView.findViewById(R.id.tvLikeCount);
        }
    }
}
