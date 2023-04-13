package com.example.projectmain.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.ImageActivity;
import com.example.projectmain.Model.Image;
import com.example.projectmain.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    ImageView image;
    private ArrayList<Image> imageList;
    Context context;
    public ImageAdapter(ArrayList<Image> i, Context context) {
        super();
        imageList = i;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.img_post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image imagePost = imageList.get(position);
        image.setImageResource(imagePost.getResource());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int pos, boolean b) {
                Intent i= new Intent(context, ImageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putInt("ImgRes", imagePost.getResource());

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
