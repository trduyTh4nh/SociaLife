package com.example.projectmain.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    Boolean isEmpty = false;
    private static final String SHARE_PRE_NAME = "mypref";
    private static final String KEY_NAME = "name";
    SharedPreferences sharedPreferences;

    Activity a;
    String name;
    public ImageAdapter(List<Post> imageList, Context context, Activity a) {
        this.imageList = imageList;
        this.context = context;
        this.a = a;
        if(imageList.size() == 0){
            isEmpty = true;
        }
        sharedPreferences = context.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_NAME, null);
    }

    private List<Post> imageList;

    Context context;
    DB db;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(isEmpty){
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.error, parent,false));
        }
        db = new DB(context);
        View view = LayoutInflater.from(context).inflate(R.layout.img_post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (isEmpty) {
            holder.tvError.setText("Không có bài đăng hình ảnh.");
            holder.tvErrorMsg.setText("Người dùng này chưa có bài đăng nào có hình ảnh.");
            return;
        }
        Post imagePost = imageList.get(position);
        Uri imgPost = Uri.parse(imagePost.getImgPost());
        Log.d("d", imagePost.getImgPost());
        // méo hiểu
        holder.image.setImageURI(imgPost);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type;
                Intent i = new Intent(context, PostDetailActitivty.class);
                int id = imagePost.getIduser();
                User u = db.getUser(id);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String ava = db.getImagefor(imagePost.getIduser());
                Bundle bn = new Bundle();
                if (!imagePost.getImgPost().equals("null") && imagePost.getContent().equals("null")) {
                    type = 0;
                } else {
                    type = 3;
                }
                if (type == 0) {
                    bn.putString("Img", imagePost.getImgPost());
                } else {
                    bn.putString("Content", imagePost.getContent());
                    bn.putString("Img", imagePost.getImgPost());
                }
                bn.putInt("idPost", imagePost.getId());
                bn.putString("Username", u.getName());
                bn.putInt("idUser", imagePost.getIduser());
                bn.putString("Pfp", ava);
                bn.putString("Name", u.getName());
                bn.putBoolean("IsCmt", true);
                bn.putInt("ViewType", type);
                bn.putInt("idUser", imagePost.getIduser());
                String timedifference = TimeHelper.getTime(imagePost.getTime());
                bn.putString("Time", timedifference);
                i.putExtras(bn);
                context.startActivity(i);
            }
        });
        if (db.getIduser(name) == imagePost.getIduser()) {
            holder.image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu menu = new PopupMenu(context, view);
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
                                            db.removePost(imagePost.getId());
                                            imageList.remove(position);
                                            notifyItemRemoved(position);
                                        }
                                    });
                                    builder.setNegativeButton("Hủy", null);
                                    builder.create().show();
                                    break;
                                case R.id.edit_post:
                                    Intent i = new Intent(context, EditPostActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Bundle bd = new Bundle();
                                    bd.putInt("idPost", imagePost.getId());
                                    i.putExtras(bd);
                                    context.startActivity(i);
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
        if(isEmpty)
            return 1;
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvError, tvErrorMsg;
        private ImageView image;
        private ItemClickListener i;
        public ViewHolder(@NonNull View v){
            super(v);
            image = v.findViewById(R.id.ivImage);
            tvErrorMsg = itemView.findViewById(R.id.tvErrorMsg);
            tvError = itemView.findViewById(R.id.tvError);
            v.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener listener){
            this.i = listener;
        }
        @Override
        public void onClick(View view) {
            //i.onClick(view, getAdapterPosition(), false);
        }
    }

    public interface ItemClickListener{
        void onClick(View v, int pos, boolean b);
    }
}
