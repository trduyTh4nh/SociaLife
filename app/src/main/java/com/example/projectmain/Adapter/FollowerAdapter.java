package com.example.projectmain.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Follower;
import com.example.projectmain.R;
import com.example.projectmain.UserActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerHolder>{
    Context c;
    Boolean isEmpty;
    public FollowerAdapter(Context c, List<Follower> followers) {
        this.c = c;
        this.followers = followers;
        isEmpty = followers.size() == 0;
    }

    List<Follower> followers;
    DB db;

    @NonNull
    @Override
    public FollowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(isEmpty){
            return new FollowerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.error, parent, false));
        }
        db = new DB(c);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_item, parent, false);
        return new FollowerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerHolder holder, @SuppressLint("RecyclerView") int position) {
        if(isEmpty){
            holder.tvError.setText("Bạn chưa theo dõi ai cả.");
            holder.tvErrorMsg.setText("Hãy theo dõi một người dùng và họ sẽ xuất hiện ở đây!");
            return;
        }
        Follower follower = followers.get(position);

        if(follower == null)
            return;
        ///// chưa xử lý ảnh
        holder.img.setImageURI(Uri.parse(follower.getAvatar()));
        holder.name.setText(follower.getName());
        AlertDialog.Builder b = new AlertDialog.Builder(c);
        b.setTitle("Hủy theo dõi");
        b.setMessage("Bạn có muốn hủy theo dõi người dùng " + follower.getName() + "? Bạn sẽ không thể thấy thông báo khi họ đăng bài, cũng như là bài đăng của họ trên trang chủ.");
        b.setPositiveButton("Hủy theo dõi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int id = db.getIduser(follower.getName());
                db.UnFollower(id);
                if(followers.size() == 1){
                    followers.remove(0);
                } else
                    followers.remove(position);
                notifyItemRemoved(position);
            }
        });
        b.setNegativeButton("Hủy", null);
        holder.stateFollow.setOnClickListener(v -> {
            AlertDialog a = b.create();
            a.show();
        });
        holder.btnUsr.setOnClickListener(v -> {
            Bundle bd = new Bundle();
            bd.putInt("idUser", db.getIduser(follower.getName()));

            Intent i = new Intent(c.getApplicationContext(), UserActivity.class);
            i.putExtras(bd);
            c.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        if(followers != null){
            if(isEmpty){
                return 1;
            }
            return followers.size();
        }
        return 0;
    }

    public class FollowerHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name, userName;
        private Button stateFollow;
        LinearLayout btnUsr;
        private TextView tvError, tvErrorMsg;

        public FollowerHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.avatar);
            name = view.findViewById(R.id.name);
            stateFollow = view.findViewById(R.id.stateFollow);
            btnUsr = view.findViewById(R.id.btnUsr);
            tvErrorMsg = view.findViewById(R.id.tvErrorMsg);
            tvError = view.findViewById(R.id.tvError);
        }
    }
}