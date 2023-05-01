package com.example.projectmain.Adapter;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.SpeechRecognizer;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.ImageActivity;
import com.example.projectmain.Model.Image;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.PostDetailActitivty;
import com.example.projectmain.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    Boolean isEmpty = false;
    public ImageAdapter(List<Post> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
        if(imageList.size() == 0){
            isEmpty = true;
        }
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
        if(isEmpty){
            holder.tvError.setText("Không có bài đăng hình ảnh.");
            holder.tvErrorMsg.setText("Người dùng này chưa có bài đăng nào có hình ảnh.");
            return;
        }
        Post imagePost = imageList.get(position);
        Uri imgPost = Uri.parse(imagePost.getImgPost());
        Log.d("d", imagePost.getImgPost());
        // méo hiểu
        holder.image.setImageURI(imgPost);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int pos, boolean b) {
                int type;
                Intent i= new Intent(context, PostDetailActitivty.class);
                int id = imagePost.getIduser();
                User u = db.getUser(id);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String ava = db.getImagefor(imagePost.getIduser());
                Bundle bn = new Bundle();
                if(!imagePost.getImgPost().equals("null") && imagePost.getContent().equals("null")){
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
                bn.putString("Time", imagePost.getTime());
                i.putExtras(bn);
                context.startActivity(i);
            }
        });
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
            i.onClick(view, getAdapterPosition(), false);
        }
    }

    public interface ItemClickListener{
        void onClick(View v, int pos, boolean b);
    }
}
