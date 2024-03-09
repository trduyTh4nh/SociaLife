package com.example.projectmain;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.User;
import com.google.android.material.imageview.ShapeableImageView;

public class SettingActivity extends AppCompatActivity {
    ImageButton btnExit, btnInfo;
    ImageButton btnLogout;

    LinearLayout btnUpdate;
    ShapeableImageView ivAvatar;
    TextView tvName, tvEmail;
    LinearLayout btnLogoff;

    LinearLayout btnListFollow;
    private static final String SHARED_PREF_NAME = "mypref";

    private static final String KEY_IMAGE_LINK = "linkImage";

    private static final String KEY_NAME = "name";

    private static final String KEY_EMAIL = "email";

    SharedPreferences sharedPreferences;
    DB db;

    User user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        DB db = new DB(this);
        btnLogoff = findViewById(R.id.btnLogoff);
        btnExit = findViewById(R.id.btn_exit);
        tvName = findViewById(R.id.setting_userName);
        tvEmail = findViewById(R.id.setting_email);
        ivAvatar = findViewById(R.id.ivAvatar);
        btnListFollow = findViewById(R.id.btnFlolow);
        btnInfo = findViewById(R.id.btn_info);
        btnInfo.setOnClickListener(v -> {
            Intent i = new Intent(SettingActivity.this, AppCreditsActivity.class);
            startActivity(i);
        });
        btnListFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, FollowerActivity.class);
                startActivity(i);
            }
        });


        db = new DB(getApplicationContext());

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String imgUrl = sharedPreferences.getString(KEY_IMAGE_LINK, null);
        Uri link = null;
        user = db.getUser(email);
        String strImageAvatar = db.getImagefor(user.getId());


        Log.d("IMG: ", String.valueOf(link));

//            ivAvatar.setImageResource(R.drawable.def);
        if (name != null) {
            if (strImageAvatar != null) {
                link = Uri.parse(strImageAvatar);
                ivAvatar.setImageURI(link);
            } else {
                ivAvatar.setImageResource(R.drawable.def);
            }
            tvName.setText(name);
            tvEmail.setText(email);
        }
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder d = new AlertDialog.Builder(SettingActivity.this);
                d.setTitle("Đăng xuất");
                d.setMessage("Bạn có chắc là muốn đăng xuất không?");
                d.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        Intent j = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(j);
                    }
                });
                d.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog a = d.create();
                a.show();
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
    protected void onResume() {
        super.onResume();
        db = new DB(SettingActivity.this);
        String strImageAvatar = db.getImagefor(user.getId());

        String name = sharedPreferences.getString(KEY_NAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        //  String imgUrl = sharedPreferences.getString(KEY_IMAGE_LINK, null);
        Uri link = null;
        if (strImageAvatar != null) {
            link = Uri.parse(strImageAvatar);
        }
        if (name != null) {
            if (link == null) {
                ivAvatar.setImageResource(R.drawable.def);
            } else {
                ivAvatar.setImageURI(link);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        this.overridePendingTransition(R.anim.in_right, R.anim.out_right);
    }
}