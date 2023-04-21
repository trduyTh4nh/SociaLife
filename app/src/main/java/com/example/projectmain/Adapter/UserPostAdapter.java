package com.example.projectmain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Post;
import com.example.projectmain.R;

import java.util.ArrayList;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.PostViewHolder> {
    Context c;
    ArrayList<Post> Posts;
    public UserPostAdapter(Context c, ArrayList<Post> posts){
        this.c = c;
        Posts = posts;
    }

    @Override
    public int getItemViewType(int position) {
        if(Posts.get(position).getImgPost().equals("null")){
            return 0;
        }
        return 1;
        }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.viewpager_layout, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if(position == 0){
            ImageAdapter adapter = new ImageAdapter(Posts, c);
            holder.rcvPosts.setAdapter(adapter);
            GridLayoutManager g = new GridLayoutManager(c,3);
            holder.rcvPosts.setLayoutManager(g);
        } else if(position == 1){
            TextPostAdapter adapter = new TextPostAdapter(c, Posts);
            LinearLayoutManager l = new LinearLayoutManager(c);
            holder.rcvPosts.setAdapter(adapter);
            holder.rcvPosts.setLayoutManager(l);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rcvPosts;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvPosts = itemView.findViewById(R.id.rcvPosts);
        }
    }
    public int CheckText(){
        int e = 0;
        for (Post p : Posts) {
            if(!p.getContent().equals("null")){
                e++;
            }
        }
        return e;
    }
}
