package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class RelationActivity extends AppCompatActivity {
    Timer timerChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        timerChange = new Timer();
        timerChange.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent  intent = new Intent(RelationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}