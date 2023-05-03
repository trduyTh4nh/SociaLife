package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projectmain.Adapter.FollowerAdapter;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Follower;
import com.example.projectmain.Model.User;

import java.util.ArrayList;
import java.util.List;

public class FollowerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FollowerAdapter adapter;
    ImageButton exitBTN;
    TextView tvCount;

    SharedPreferences sharedPreferences;
    private static final String SHARE_PRE_NAME = "mypref";
    private static final String KEY_MAIL = "email";

    DB db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        tvCount = findViewById(R.id.tvFollowCount);
        exitBTN = findViewById(R.id.btn_exit);
        exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sharedPreferences = getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_MAIL, null);

        db = new DB(FollowerActivity.this);

        User currentUser = db.getUser(email);
        recyclerView = findViewById(R.id.rview);
        adapter = new FollowerAdapter(this, listFollowers(currentUser.getId()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        tvCount.setText("Đang theo dõi " + String.valueOf(listFollowers(currentUser.getId()).size()) + " người");
    }

    public List<Follower> listFollowers(int idCurrentUser) {
        List<Follower> list = new ArrayList<>();
        SQLiteDatabase database = db.getWritableDatabase();

        Cursor cursor = database.query("follower", null, "iduser = ?", new String[]{String.valueOf(idCurrentUser)}, null,null,null);

        while (cursor.moveToNext()){
            int idFollowing = cursor.getInt(2);
            String avatarUser = db.getImgAvata(idFollowing);
            String userNme = db.getName(idFollowing);
            String name = db.getName(idFollowing);
            boolean state = true;

            list.add(new Follower(avatarUser, name, userNme, state));
        }

        return list;
    }
}