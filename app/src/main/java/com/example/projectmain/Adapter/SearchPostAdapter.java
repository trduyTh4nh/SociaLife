package com.example.projectmain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Post;
import com.example.projectmain.R;

import java.util.ArrayList;

public class SearchPostAdapter extends RecyclerView.Adapter<SearchPostAdapter.ViewHolder>{


    private ArrayList<Post> posts;
    private Context context;

    public SearchPostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post=posts.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView avatarSearch,img_postSearch;
        TextView nameSearch,nameu_userSearch,content_postSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarSearch=itemView.findViewById(R.id.avatarSearch);
            img_postSearch=itemView.findViewById(R.id.img_postSearch);
            nameSearch=itemView.findViewById(R.id.nameSearch);
            nameu_userSearch=itemView.findViewById(R.id.nameu_userSearch);
            content_postSearch=itemView.findViewById(R.id.content_postSearch);
        }
    }
}
