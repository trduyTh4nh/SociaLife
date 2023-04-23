package com.example.projectmain.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder>{
    Context c;
    ArrayList<User> usrs;
    public UserSearchAdapter(Context c, ArrayList<User> usrs){
        this.c = c;
        this.usrs = usrs;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
            return;
        }
        holder.ivProfile.setImageURI(Uri.parse(db.getImagefor(usr.getId())));
    }

    @Override
    public int getItemCount() {
        return usrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Button btnFlo;
        TextView tvName, tvDesc;
        ImageView ivProfile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivProfile = itemView.findViewById(R.id.ivAvatar);
        }
    }
}
