package com.example.projectmain.Adapter;

import android.content.Context;
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

import com.example.projectmain.Model.Post;
import com.example.projectmain.R;
import com.google.android.material.imageview.ShapeableImageView;

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

        holder.btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), v);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_post:
                                Toast.makeText(context, "Post", Toast.LENGTH_SHORT).show();
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

            btnOpenMenu = (ImageButton) view.findViewById(R.id.btnOptions);
        }


    }

}
