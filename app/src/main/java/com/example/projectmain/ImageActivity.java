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
    Toolbar tbActionbar, tbBottom;
    ImageView ivPicture, ivPfp;
    TextView tvPname, tvUsername;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        // init
        initView();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i = getIntent();
        Bundle b = i.getExtras();
        ivPicture.setImageResource(b.getInt("ImgRes"));

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    tbActionbar.setVisibility(View.VISIBLE);
                    tbBottom.setVisibility(View.VISIBLE);
                    clicked = false;
                    return;
                } else {
                    clicked = true;
                    tbActionbar = findViewById(R.id.tbActionbar);
                    tbBottom = findViewById(R.id.tbBottomBar);
                    tbActionbar.setVisibility(View.INVISIBLE);
                    tbBottom.setVisibility(View.INVISIBLE);
                }
            }
        });
        String poster = b.getString("ImgPoster");
        String username = b.getString("ImgUsername");
        int avtRes = b.getInt("ImgPfp");
        if (b.getString("ImgPoster") != null && b.getString("ImgUsername") != null) {
            tvPname.setText(poster);
            tvUsername.setText(username);
            ivPfp.setImageResource(avtRes);
        }
    }

     void initView(){
        btnClose = findViewById(R.id.btnClose);
        ivPicture = findViewById(R.id.ivImage);
        tvPname = findViewById(R.id.tvPName);
        tvUsername = findViewById(R.id.tvUsername);
        ivPfp = findViewById(R.id.ivPfp);
    }
}