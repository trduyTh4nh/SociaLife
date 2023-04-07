package com.example.projectmain.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.ImageActivity;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.PostDetailActitivty;
import com.example.projectmain.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.sql.Time;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public PostAdapter(Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;
    }

    Context context;
    List<Post> posts;
    List<User> users;
    List<String> listName;
    DB db;


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
     //   User user = users.get(position);


        if (post == null)
            return;
      //  db = new DB(context.getApplicationContext());

        //


        holder.avatar.setImageURI(Uri.parse(post.getAvatar()));
        holder.imgPost.setImageURI(Uri.parse(post.getImgPost()));
        holder.name.setText(post.getName());
        holder.userName.setText(post.getUsername());
        holder.numberLike.setText(post.getNumber_like());
        holder.content.setText(post.getContent());

        Time now = new Time(position);

        int timer = now.getHours() / 24;

        if (timer < 1) {
            holder.time.setText(timer + " giờ trước");
        } else if (timer > 14) {
            holder.time.setText(timer / 7 + " tuần trước");
        } else if (timer / 7 > 5) {
            holder.time.setText(timer / 30 + " tháng trước");
        } else {
            holder.time.setText(timer + " ngày trước");
        }
        holder.imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ImageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bd = new Bundle();
                bd.putString("ImgRes", post.getImgPost());
                bd.putString("ImgPoster", post.getName());
                bd.putString("ImgUsername", post.getUsername());
                bd.putString("ImgPfp", post.getAvatar());
                i.putExtras(bd);
                context.startActivity(i);
            }
        });

        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PostDetailActitivty.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("Username", post.getUsername());
                bundle.putString("Img", post.getImgPost());
                bundle.putString("Pfp", post.getAvatar());
                bundle.putString("Name", post.getName());
                bundle.putBoolean("IsCmt", true);

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });


        // menu
        holder.btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), v);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_post:
                                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.remove_post:
                                Toast.makeText(context, "Remove", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.hide_post:
                                Toast.makeText(context, "Hide", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;

                        }
                        return true;
                    }
                });
                popupMenu.inflate(R.menu.menu_option_post);
                popupMenu.show();
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

        private ImageButton btnOpenMenu;

        private ImageButton btnComment;
        private ShapeableImageView avatar;
        private ImageView imgPost;
        private TextView name, userName, numberLike, content, time, nameUserPost;

        public PostViewHolder(@NonNull View view) {
            super(view);
            avatar = (ShapeableImageView) view.findViewById(R.id.avatar);
            imgPost = (ImageView) view.findViewById(R.id.img_post);
            name = (TextView) view.findViewById(R.id.name);
            userName = (TextView) view.findViewById(R.id.nameu_user);
            nameUserPost = (TextView) view.findViewById(R.id.nameuser_post);
            numberLike = (TextView) view.findViewById(R.id.number_like);
            content = (TextView) view.findViewById(R.id.content_post);
            time = (TextView) view.findViewById(R.id.time_post);
            btnComment = (ImageButton) view.findViewById(R.id.btn_Pcomment);
            btnOpenMenu = (ImageButton) view.findViewById(R.id.btnOptions);
        }


    }

}
