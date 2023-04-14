package com.example.projectmain.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Class.Post;
import com.example.projectmain.ImageActivity;
import com.example.projectmain.PostInfoActivity;
import com.example.projectmain.R;
import com.example.projectmain.UserActivity;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public PostAdapter( Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;
    }
    Context context;
    List<Post> posts;

    @Override
    /*
    * Lấy kiểu view
    * Hàm này được dùng để lấy kiểu view
    * Tham số: position: int: Dùng để lấy vị trí hiện tại đang xét
    * Các kiểu view:
    * 0: Post có hình nhưng ko caption
    * 1: Post có caption ngắn
    * 2: Post có caption dài
    * 3: Post vừa có hình, vừa có caption
    * */
    public int getItemViewType(int position) {
        String postContent = posts.get(position).getContent();
        int img = posts.get(position).getImgPost();
        if(postContent == null && img != -1){
            return 0;
        }
        if(postContent.length() <= 50 && img == -1){
            return 1;
        }
        if(postContent.length() >= 50 && img == -1){
            return 2;
        }
        if(postContent != null && img != -1){
            return 3;
        }
        return 4;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        /*
        * Inflate các kiểu View:
        * 0: Post có hình nhưng ko caption
        * 1: Post có caption ngắn
        * 2: Post có caption dài
        * 3: Post vừa có hình, vừa có caption
        * 4: Lỗi ko có dữ liệu
        * */

        if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_img_notext, parent, false);
        } else if(viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_small_paragraph, parent, false);
        } else if(viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_large_paragraph, parent, false);
        } else if(viewType == 3){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.error, parent, false);
        }
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        int type = getItemViewType(position);
        if (post == null)
            return;
        /* Lưu ý các loại View
        * 0: Post có hình nhưng ko caption
        * 1: Post có caption ngắn
        * 2: Post có caption dài
        * 3: Post vừa có hình, vừa có caption
         * */
        if(type == 0){
            //View 0: Hình ko caption
            /*
            * Hình ko caption, chỉ cần setImage và setOnClick cho imgPost, không cần set cho tvContent thứ khác (sẽ gây nullPointerException)
            */
            holder.imgPost.setImageResource(post.getImgPost());
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
        } else if(type == 1 || type == 2){
            //View 1, View 2: Có caption nhưng ko có hình
            /*
             * Post chỉ có mỗi caption, nên chỉ setText cho mỗi caption, cố gắng setImage sẽ gây nullPointerException
             */
            holder.content.setText(post.getContent());
        } else if(type == 3){
            //View 1, View 2: Có caption và hình
            /*
             * Post có cả 2 caption và hình, setImageResource và setText cho imgPost và content bình tường
             */
            holder.imgPost.setImageResource(post.getImgPost());
            holder.content.setText(post.getContent());
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
        }
        holder.avatar.setImageResource(post.getAvatar());
        holder.name.setText(post.getName());
        holder.userName.setText(post.getUsername());
        holder.numberLike.setText(post.getNumber_like());
        holder.time.setText(post.getTime());
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bn = new Bundle();
                if(type == 0){
                    bn.putInt("Img", post.getImgPost());
                } else if(type == 1 || type == 2){
                    bn.putString("Content", post.getContent());
                } else if(type == 3){
                    bn.putString("Content", post.getContent());
                    bn.putInt("Img", post.getImgPost());
                }
                bn.putString("Username", post.getUsername());
                bn.putInt("Pfp", post.getAvatar());
                bn.putString("Name", post.getName());
                bn.putBoolean("IsCmt", true);
                bn.putInt("ViewType", type);
                intent.putExtras(bn);
                context.startActivity(intent);
            }
        });
        holder.llUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, UserActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                Bundle b = new Bundle();
                b.putString("Username", post.getUsername());
                b.putString("Name", post.getName());
                b.putInt("Img", post.getImgPost());
                i.putExtras(b);
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
        private TextView name, userName, numberLike, content, time, nameUserPost, tvParagraph;
        private ImageButton btnComment;
        LinearLayout llUser;
        public PostViewHolder(@NonNull View view) {
            super(view);
            llUser = view.findViewById(R.id.llUser);
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
