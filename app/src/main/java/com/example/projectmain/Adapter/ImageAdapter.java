package com.example.projectmain.Adapter;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Class.ImageClass;
import com.example.projectmain.ImageActivity;
import com.example.projectmain.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    public interface ItemClickListener{
        void onClick(View v, int pos, boolean b);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ImageClass imgC = imageList.get(i);
        img.setImageResource(imgC.getResource());
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int pos, boolean b) {
                Intent i = new Intent(context, ImageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bd = new Bundle();
                bd.putInt("ImgRes", imgC.getResource());
                i.putExtras(bd);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemClickListener i;
        public ViewHolder(@NonNull View v){
            super(v);
            img = v.findViewById(R.id.ivImage);
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
    private ArrayList<ImageClass> imageList;
    ImageView img;
    Context context;
    public ImageAdapter(@NonNull Context context, ArrayList<ImageClass> i) {
        super();
        this.context = context;
        imageList = i;
    }

}
