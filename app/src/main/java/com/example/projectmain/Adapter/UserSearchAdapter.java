package com.example.projectmain.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;
import com.example.projectmain.UserActivity;

import java.util.ArrayList;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder>{
    Context c;
    SharedPreferences sharedPreferences;
    String name;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    ArrayList<User> usrs;
    public UserSearchAdapter(Context c, ArrayList<User> usrs){
        sharedPreferences = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        this.c = c;
        this.usrs = usrs;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        name = sharedPreferences.getString(KEY_NAME, null);
        View v = LayoutInflater.from(c).inflate(R.layout.user_entry, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DB db = new DB(c);

        SQLiteDatabase d = db.getReadableDatabase();
        User usr = usrs.get(position);
        holder.tvName.setText(usr.getName());
        if(db.getImagefor(usr.getId()) == null){
            holder.ivProfile.setImageResource(R.drawable.def);
        } else
            holder.ivProfile.setImageURI(Uri.parse(db.getImagefor(usr.getId())));
        holder.layoutUser.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putInt("idUser", usr.getId());
            Intent i = new Intent(c, UserActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtras(b);
            c.startActivity(i);
        });
        if(db.CheckNameinFollowing(usr.getId(), db.getIduser(name))){
            holder.btnUnflo.setVisibility(View.VISIBLE);
            holder.btnFlo.setVisibility(View.GONE);
        } else if(db.getIduser(name) == usr.getId()){
            holder.btnFlo.setVisibility(View.GONE);
        } else {
            holder.btnUnflo.setVisibility(View.GONE);
            holder.btnFlo.setVisibility(View.VISIBLE);
        }

        holder.btnFlo.setOnClickListener(v -> {
            db.insertDataFollow(db.getIduser(name), usr.getId());
            notifyItemChanged(position);
        });
        holder.btnUnflo.setOnClickListener(v -> {
            AlertDialog.Builder b = new AlertDialog.Builder(c);
            b.setTitle("Hủy theo dõi");
            b.setMessage("Bạn có muốn hủy theo dõi người dùng " + usr.getName() + "? Bạn sẽ không thể thấy thông báo khi họ đăng bài, cũng như là bài đăng của họ trên trang chủ.");
            b.setPositiveButton("Hủy theo dõi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int id = db.getIduser(usr.getName());
                    db.UnFollower(id);
                    notifyItemChanged(position);
                }
            });
            b.setNegativeButton("Hủy", null);
            b.create().show();
        });
    }

    @Override
    public int getItemCount() {
        return usrs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        Button btnFlo, btnUnflo, btnSelf;
        TextView tvName, tvDesc;
        ImageView ivProfile;
        LinearLayout layoutUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutUser = itemView.findViewById(R.id.clickto);
            tvName = itemView.findViewById(R.id.tvName);
            ivProfile = itemView.findViewById(R.id.ivAvatar);
            btnFlo = itemView.findViewById(R.id.btnFollow);
            btnUnflo = itemView.findViewById(R.id.btnUnFollow);
            btnSelf = itemView.findViewById(R.id.btnSelf);
        }
    }
}
