package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

public class ImageActivity extends AppCompatActivity {
    ImageButton btnClose;
    Boolean clicked = false;
    Toolbar tbAction;
    Toolbar tbBottom;
    ImageView ivPicture, ivPfp;
    TextView tvPName, tvUsername;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ivPicture = findViewById(R.id.ivImage);
        btnClose = findViewById(R.id.btnClose);
        tvPName = findViewById(R.id.tvPName);
        tvUsername = findViewById(R.id.tvUsername);
        ivPfp = findViewById(R.id.ivPfp);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent i = getIntent();
        Bundle b = i.getExtras();
        ivPicture.setImageResource(b.getInt("ImgRes"));
        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked){
                    tbAction.setVisibility(View.VISIBLE);
                    tbBottom.setVisibility(View.VISIBLE);
                    clicked = false;
                    return;
                }
                clicked = true;
                tbAction = findViewById(R.id.tbActionbar);
                tbBottom = findViewById(R.id.tbBottomBar);
                tbAction.setVisibility(View.INVISIBLE);
                tbBottom.setVisibility(View.INVISIBLE);
            }
        });
        String poster = b.getString("ImgPoster");
        String username = b.getString("ImgUsername");
        int avtRes = b.getInt("ImgPfp");
        if(b.getString("ImgPoster") != null && b.getString("ImgUsername") != null){
            tvPName.setText(poster);
            tvUsername.setText(username);
            ivPfp.setImageResource(avtRes);
        }
    }
}