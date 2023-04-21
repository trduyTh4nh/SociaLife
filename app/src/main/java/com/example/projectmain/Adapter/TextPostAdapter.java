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
    public TextPostAdapter(Context c, ArrayList<Post> posts){
        this.c = c;
        this.posts = posts;
    }
    @NonNull
    @Override
    public TPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.text_post, parent, false);
        return new TPHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TPHolder holder, int position) {
        holder.tvContent.setText(posts.get(position).getContent());
        holder.tvLike.setText(posts.get(position).getNumber_like());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class TPHolder extends RecyclerView.ViewHolder{
        private TextView tvContent, tvLike;

        public TPHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvLike = itemView.findViewById(R.id.tvLikeCount);
        }
    }
}
