package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.User;

public class EditInfo_Activity extends AppCompatActivity {
    EditText edtName, edtEmail, edtUserName, edtPassword, edtConfirmPass, edtStory;
    ImageView imgCurrent;
    Button btnSave;
    ImageButton btnChangeImage;
    DB db;
    SharedPreferences sharedPreferences;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initView();
        // handle
    }

    public void initView(){
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtUserName = findViewById(R.id.edtUsrname);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPass = findViewById(R.id.edt_confirmPass);
        edtStory = findViewById(R.id.edt_story);

        imgCurrent = findViewById(R.id.avart_current);
        btnSave = findViewById(R.id.btnSave);
        btnChangeImage = findViewById(R.id.btnEdit_image);
    }
}