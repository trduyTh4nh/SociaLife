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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.ImageActivity;
import com.example.projectmain.Model.Image;
import com.example.projectmain.Model.Post;
import com.example.projectmain.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    public ImageAdapter(List<Post> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    private List<Post> imageList;

    Context context;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.img_post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post imagePost = imageList.get(position);
        Uri imgPost = Uri.parse(imagePost.getImgPost());
        Log.d("d", imagePost.getImgPost());
        // méo hiểu
        holder.image.setImageURI(imgPost);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int pos, boolean b) {
                Intent i= new Intent(context, ImageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("ImgRes", imagePost.getImgPost());
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    
    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView image;
        private ItemClickListener i;
        public ViewHolder(@NonNull View v){
            super(v);
            image = v.findViewById(R.id.ivImage);

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
