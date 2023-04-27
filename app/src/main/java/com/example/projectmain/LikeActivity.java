package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.projectmain.Adapter.UserSearchAdapter;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Fragment.HomeFragment;
import com.example.projectmain.Model.User;

import java.util.ArrayList;

public class LikeActivity extends AppCompatActivity {
    ArrayList<User> users = new ArrayList<User>();
    DB database;
    UserSearchAdapter a;
    RecyclerView rcvLike;
    ImageButton btnExits;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        id = b.getInt("idPost");
        setContentView(R.layout.activity_like);
        rcvLike = findViewById(R.id.rcvLikeList);
        database = new DB(this);
        getContent();
        a = new UserSearchAdapter(this, users);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rcvLike.setAdapter(a);
        rcvLike.setLayoutManager(l);
        btnExits = findViewById(R.id.btn_exit);

        btnExits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @SuppressLint("Range")
    void getContent(){
        if(users == null){
            users = new ArrayList<User>();
        } else {
            users.clear();
        }

        Cursor c = database.getLikeUser(id);
        while(c.moveToNext()){
            users.add(new User(c.getInt(0), c.getString(1), c.getString(6)));
        }
        c.close();
    }

}