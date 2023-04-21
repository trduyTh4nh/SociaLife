package com.example.projectmain.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.Follower;
import com.example.projectmain.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerHolder>{

    public FollowerAdapter(List<Follower> followers) {
        this.followers = followers;
    }

    List<Follower> followers;

    @NonNull
    @Override
    public FollowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_item, parent, false);
        return new FollowerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerHolder holder, int position) {
        Follower follower = followers.get(position);

        if(follower == null)
            return;
        ///// chưa xử lý ảnh
        holder.img.setImageURI(Uri.parse(follower.getAvatar()));
        holder.name.setText(follower.getName());
        holder.userName.setText(follower.getUserName());
        holder.stateFollow.setChecked(follower.isState());
    }

    @Override
    public int getItemCount() {
        if(followers != null)
            return followers.size();
        return 0;
    }

    public class FollowerHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView img;
        private TextView name, userName;
        private CheckBox stateFollow;

        public FollowerHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.avatar);
            name = view.findViewById(R.id.name);
            userName = view.findViewById(R.id.userName);
            stateFollow = view.findViewById(R.id.stateFollow);
        }
    }
}