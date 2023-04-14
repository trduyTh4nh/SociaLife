package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projectmain.Adapter.ImageAdapter;
import com.example.projectmain.Class.ImageClass;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    ImageButton btnHome;
    TextView tvName, tvUsername, tvBio;
    RecyclerView rcvPosts;
    int[] imageRes = {R.drawable.imgquang, R.drawable.meo, R.drawable.imgcrew, R.drawable.imgpc};
    private final ArrayList<ImageClass> list = new ArrayList<ImageClass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        for(int i = 0; i < imageRes.length; i++){
            list.add(new ImageClass(imageRes[i]));
        }
        ImageAdapter adap = new ImageAdapter(this, list);
        rcvPosts = findViewById(R.id.rcvImages);
        rcvPosts.setAdapter(adap);
        GridLayoutManager g = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        rcvPosts.setLayoutManager(g);
        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}