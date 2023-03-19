package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        this.overridePendingTransition(R.anim.in_right, R.anim.out_right);
    }
}