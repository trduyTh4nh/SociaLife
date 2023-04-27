package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Post;

public class EditPostActivity extends AppCompatActivity {
    int id;
    DB db;
    Post p;
    EditText edtContent;
    TextView tvNoImg;
    ImageView ivPost;
    ImageButton btnSave, btnDelete, btnExit;
    Button resotreImage;
    ConstraintLayout ImageWrapper;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    SharedPreferences share;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        resotreImage = findViewById(R.id.btnRedo);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_deleteimg);
        share = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        ImageWrapper = findViewById(R.id.ImageWrapper);
        tvNoImg = findViewById(R.id.noImage);
        btnExit = findViewById(R.id.btn_exit);
        String name = share.getString(KEY_NAME, null);
        db = new DB(this);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        id = b.getInt("idPost");
        Uri tempUri = null;
        String tempImage = null;
        p = db.getPostFromID(id, name);
        if(p.getImgPost().equals("null")){
            ImageWrapper.setVisibility(View.GONE);
            tvNoImg.setVisibility(View.VISIBLE);
        } else {
            tempUri = Uri.parse(p.getImgPost());
            tempImage = p.getImgPost();
        }

        edtContent = findViewById(R.id.edtEdit);
        ivPost = findViewById(R.id.img_post);
        edtContent.setText(p.getContent());
        ivPost.setImageURI(Uri.parse(p.getImgPost()));
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtContent.getText().toString().equals("")){
                    Toast.makeText(EditPostActivity.this, "Nội dung bị trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                p.setContent(edtContent.getText().toString());
                long l = db.UpdatePost(p);
                if(l == 0){
                    Toast.makeText(EditPostActivity.this, "lỗi!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setImgPost("null");
                ImageWrapper.setVisibility(View.GONE);
                resotreImage.setVisibility(View.VISIBLE);
            }
        });
        Uri finalTempUri = tempUri;
        String finalTempImage = tempImage;
        resotreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivPost.setImageURI(finalTempUri);
                p.setImgPost(finalTempImage);
                ImageWrapper.setVisibility(View.VISIBLE);
                resotreImage.setVisibility(View.GONE);
            }
        });
    }
}