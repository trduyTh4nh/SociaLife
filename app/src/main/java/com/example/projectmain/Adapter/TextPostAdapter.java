package com.example.projectmain.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.EditPostActivity;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.Model.TimeHelper;
import com.example.projectmain.PostDetailActitivty;
import com.example.projectmain.R;

import java.util.ArrayList;

public class TextPostAdapter extends RecyclerView.Adapter<TextPostAdapter.TPHolder> {
    Context c;
    private static final String SHARE_PRE_NAME = "mypref";
    private static final String KEY_NAME = "name";
    SharedPreferences sharedPreferences;
    public Activity a;
    DB db;
    ArrayList<Post> posts;
    Boolean isEmpty = false;
    String name;
    public TextPostAdapter(Context c, ArrayList<Post> posts, Activity a){
        db = new DB(c);
        this.c = c;
        this.a = a;
        this.posts = posts;
        if(posts.size() == 0){
            posts.add(new Post());
            isEmpty = true;
        }
        sharedPreferences = c.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_NAME, null);
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
    public void onBindViewHolder(@NonNull TPHolder holder, @SuppressLint("RecyclerView") int position) {
        if (isEmpty) {
            holder.tvError.setText("Chưa có bài đăng văn bản");
            holder.tvErrorMsg.setText("Hãy đăng 1 bài đăng dưới dạng văn bản như là một trạng thái hoặc một đoạn văn.");
            return;
        }
        holder.tvContent.setText(posts.get(position).getContent());
        holder.tvLike.setText(String.valueOf(db.getLike(posts.get(position).getId()).getCount()));
        holder.Body.setOnClickListener(v -> {
            int type;
            Intent i = new Intent(c, PostDetailActitivty.class);
            int id = posts.get(position).getIduser();
            User u = db.getUser(id);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String ava = db.getImagefor(posts.get(position).getIduser());
            Bundle bn = new Bundle();
            if (posts.get(position).getContent().length() >= 50 && posts.get(position).getImgPost().equals("null")) {
                type = 2;
            } else {
                type = 1;
            }
            bn.putString("Content", posts.get(position).getContent());
            bn.putString("Content", posts.get(position).getContent());
            bn.putInt("idPost", posts.get(position).getId());
            bn.putString("Username", u.getName());
            bn.putInt("idUser", posts.get(position).getIduser());
            bn.putString("Pfp", ava);
            bn.putString("Name", u.getName());
            bn.putBoolean("IsCmt", true);
            bn.putInt("ViewType", type);
            bn.putInt("idUser", posts.get(position).getIduser());
            String timedifference = TimeHelper.getTime(posts.get(position).getTime());
            bn.putString("Time", timedifference);
            i.putExtras(bn);
            c.startActivity(i);

        });
        if (db.getIduser(name) == posts.get(position).getIduser()) {
            holder.Body.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu menu = new PopupMenu(c, view);
                    menu.inflate(R.menu.menu_option_post);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.remove_post:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(a);
                                    builder.setTitle("Xóa bài viết")
                                            .setMessage("Bạn có chắc là bạn muốn xóa bài viết này? Hành động này sẽ không thể đảo ngược.");
                                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            db.removePost(posts.get(position).getId());
                                            posts.remove(position);
                                            notifyItemRemoved(position);
                                        }
                                    });
                                    builder.setNegativeButton("Hủy", null);
                                    builder.create().show();
                                    break;
                                case R.id.edit_post:
                                    Intent i = new Intent(c, EditPostActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Bundle bd = new Bundle();
                                    bd.putInt("idPost", posts.get(position).getId());
                                    i.putExtras(bd);
                                    c.startActivity(i);
                                    break;
                            }
                            return false;
                        }
                    });
                    menu.show();
                    return false;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class TPHolder extends RecyclerView.ViewHolder {
        private TextView tvContent, tvLike, tvError, tvErrorMsg;
        LinearLayout Body, btnLike;
        public TPHolder(@NonNull View itemView) {
            super(itemView);
            tvErrorMsg = itemView.findViewById(R.id.tvErrorMsg);
            tvError = itemView.findViewById(R.id.tvError);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvLike = itemView.findViewById(R.id.tvLikeCount);
            Body = itemView.findViewById(R.id.Body);
            btnLike = itemView.findViewById(R.id.btnLike);

        }
    }
}
