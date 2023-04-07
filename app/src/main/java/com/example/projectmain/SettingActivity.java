package com.example.projectmain;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.projectmain.Database.DB;

public class SettingActivity extends AppCompatActivity {
    ImageButton btnExit;
    ImageButton btnLogout;
    LinearLayout btnUpdate;

    LinearLayout btnLogoff;
    private static final String SHARED_PREF_NAME = "mypref";

    private static final String KEY_IMAGE_LINK = "linkImage";

    private static final String KEY_NAME = "name";

    private static final String KEY_EMAIL = "email";

    SharedPreferences sharedPreferences;

    DB db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btnLogoff = findViewById(R.id.btnLogoff);
        btnExit = findViewById(R.id.btn_exit);
//        btnLogout = findViewById(R.id.btnLogout);

        db = new DB(getApplicationContext());
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        btnLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent i = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnUpdate = findViewById(R.id.btnUpdateInfo);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, EditInfoActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        this.overridePendingTransition(R.anim.in_right, R.anim.out_right);
    }
}