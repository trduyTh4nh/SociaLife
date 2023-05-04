package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppCreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_credits);
        findViewById(R.id.btn_exit).setOnClickListener(v -> {
            finish();
        });
    }
}