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
    ArrayList<Post> PostText;
    ArrayList<Post> PostImg;
    private void SplitList(){
        PostText = new ArrayList<Post>();
        PostImg = new ArrayList<Post>();
        for (Post p : Posts) {
            if(p.getImgPost().equals("null")){
                PostText.add(p);
            } else PostImg.add(p);
        }
    }
    public UserPostAdapter(Context c, ArrayList<Post> posts){
        this.c = c;
        Posts = posts;
    }

    @Override
    public int getItemViewType(int position) {
        if(Posts.size() == 0 || Posts.size() == 1){
            return 0;
        }
        if(Posts.get(position).getImgPost().equals("null") || Posts == null){
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
        SplitList();
        if(position == 0){
            ImageAdapter adapter = new ImageAdapter(PostImg, c);
            holder.rcvPosts.setAdapter(adapter);
            GridLayoutManager g = new GridLayoutManager(c,3);
            holder.rcvPosts.setLayoutManager(g);
        } else if(position == 1){
            TextPostAdapter adapter = new TextPostAdapter(c, PostText);
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
}
