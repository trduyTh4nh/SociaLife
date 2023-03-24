package com.example.projectmain.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Class.Post;
import com.example.projectmain.ImageActivity;
import com.example.projectmain.PostInfoActivity;
import com.example.projectmain.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public PostAdapter( Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;
    }
    Context context;
    List<Post> posts;


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        if (post == null)
            return;
        holder.avatar.setImageResource(post.getAvatar());
        holder.imgPost.setImageResource(post.getImgPost());
        holder.name.setText(post.getName());
        holder.userName.setText(post.getUsername());
        holder.nameUserPost.setText(post.getUsername());
        holder.numberLike.setText(post.getNumber_like());
        holder.content.setText(post.getContent());
        holder.time.setText(post.getTime());
        holder.imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ImageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bd = new Bundle();
                bd.putInt("ImgRes", post.getImgPost());
                bd.putString("ImgPoster", post.getName());
                bd.putString("ImgUsername", post.getUsername());
                bd.putInt("ImgPfp", post.getAvatar());
                i.putExtras(bd);
                context.startActivity(i);
            }
        });
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bn = new Bundle();
                bn.putString("Username", post.getUsername());
                bn.putInt("Img", post.getImgPost());
                bn.putInt("Pfp", post.getAvatar());
                bn.putString("Name", post.getName());
                bn.putBoolean("IsCmt", true);
                intent.putExtras(bn);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        return 0;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatar, imgPost;
        private TextView name, userName, numberLike, content, time, nameUserPost;
        private ImageButton btnComment;
        public PostViewHolder(@NonNull View view) {
            super(view);
            avatar = (ImageView) view.findViewById(R.id.ivPfp);
            imgPost = (ImageView) view.findViewById(R.id.img_post);
            name = (TextView) view.findViewById(R.id.tvPName);
            userName = (TextView) view.findViewById(R.id.tvUsername);
            nameUserPost = (TextView) view.findViewById(R.id.tvUser);
            numberLike = (TextView) view.findViewById(R.id.number_like);
            content = (TextView) view.findViewById(R.id.tvContent);
            time = (TextView) view.findViewById(R.id.time_post);
            btnComment = view.findViewById(R.id.btnComment);
        }
    }
}
