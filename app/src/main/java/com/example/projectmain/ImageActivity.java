package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.TimeHelper;

public class ImageActivity extends AppCompatActivity {
    ImageButton btnClose, btnLike, bntCmt;
    Boolean isLiked = false;
    Boolean clicked = false;
    Toolbar tbActionbar, tbBottom;
    ImageView ivPicture, ivPfp;
    TextView tvPname, tvUsername;
    private static final String SHARE_PRE_NAME = "mypref";
    private static final String KEY_NAME = "name";
    SharedPreferences sharedPreferences;
    DB db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        db = new DB(this);
        // init
        initView();
        sharedPreferences = getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        int id = db.getIduser(name);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i = getIntent();
        Bundle b = i.getExtras();

        ivPicture.setImageURI(Uri.parse(b.getString("ImgRes")));
        int idPost = b.getInt("idPost");
        if (db.CheckLike(id, idPost)) {
            btnLike.setImageResource(R.drawable.heart_fill);
            isLiked = true;
        }
        btnLike.setOnClickListener(v -> {
            if (isLiked) {
                db.Unlike(id, idPost);
                isLiked = false;
                btnLike.setImageResource(R.drawable.heart_line_white);
            } else {
                db.insertLikes(id, idPost);
                isLiked = true;
                btnLike.setImageResource(R.drawable.heart_fill);
            }
        });
        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    Log.d("ADebugTag", "Value: " + b.getString("ImgRes"));
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
        Uri avtRes = null;
        try {
            avtRes = Uri.parse(b.getString("ImgPfp"));
        } catch (Exception exception) {
            Toast.makeText(this, "Lỗi truy xuất hình ảnh \"mềm\". Đang dùng ảnh cứng." + " Lỗi do: " + exception, Toast.LENGTH_SHORT).show();
        }
        if (b.getString("ImgPoster") != null && b.getString("ImgUsername") != null) {
            tvPname.setText(poster);
            tvUsername.setText(username);
            ivPfp.setImageURI(avtRes);
        }

        bntCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = db.getPost(idPost);
                Intent intent = new Intent(ImageActivity.this, PostDetailActitivty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bn = new Bundle();

                bn.putString("Img", post.getImgPost());
                bn.putString("Username", post.getUsername());
                bn.putString("Pfp", post.getAvatar());
                bn.putString("Name", post.getName());
                bn.putString("Content", post.getContent());
                String time = TimeHelper.getTime(post.getTime());
                bn.putString("Time", time);
                bn.putBoolean("IsCmt", true);
                bn.putInt("idPost", post.getId());
                String content = (String) b.get("content");
                if (content.length() > 0) {
                    bn.putInt("ViewType", 3);
                } else
                    bn.putInt("ViewType", 0);

                intent.putExtras(bn);
                startActivity(intent);

            }
        });
    }

    void initView() {
        btnClose = findViewById(R.id.btnClose);
        ivPicture = findViewById(R.id.ivImage);
        tvPname = findViewById(R.id.tvPName);
        tvUsername = findViewById(R.id.tvUsername);
        ivPfp = findViewById(R.id.ivPfp);
        btnLike = findViewById(R.id.btnLike);
        bntCmt = findViewById(R.id.btnCmt);
    }
}