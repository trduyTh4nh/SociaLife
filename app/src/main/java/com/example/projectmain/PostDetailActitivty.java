package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectmain.Adapter.CommentAdapter;
import com.example.projectmain.Model.Comment;

import java.util.ArrayList;
import java.util.Random;

public class PostDetailActitivty extends AppCompatActivity {

    EditText edtComment;
    TextView tvname, tvUsername;
    ImageView ivPfp, ivImg;
    ImageButton btnExit;
    ArrayList<Comment> cmtList;
    RecyclerView rcvComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_actitivty);
        // init
        initView();
        // handle
        Intent i = getIntent();
        Bundle b = i.getExtras();


        tvname.setText(b.getString("Name"));
        tvUsername.setText(b.getString("Username"));
        ivPfp.setImageURI(Uri.parse(b.getString("Img")));
        ivImg.setImageURI(Uri.parse(b.getString("Img")));

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(!b.getBoolean("IsCmt")){
            edtComment.requestFocus();
        }
        cmtList = new ArrayList<>();
        DataAdapter();
        rcvComment = findViewById(R.id.rcvComments);
        CommentAdapter cmtAdap = new CommentAdapter(cmtList, this);
        rcvComment.setAdapter(cmtAdap);
        rcvComment.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

     void initView() {
        tvname = findViewById(R.id.tvPName);
        tvUsername = findViewById(R.id.tvUsername);
        ivPfp = findViewById(R.id.ivPfp);
        ivImg = findViewById(R.id.img_post);
        btnExit = findViewById(R.id.btnExit);
        edtComment = findViewById(R.id.edtComment);
    }

     void DataAdapter() {
        String[] names = {"@elonmusk", "@denvau", "@wuang", "@sugar", "@31.03", "@khangZata"};
        String[] comment = {"Hello hello hi hi", "ok!", "bài đăng hay", "tao random", "ứng dụng rác, vứt đi"};
        Random r = new Random();
        for(int i = 0; i < 20; i++){
            cmtList.add(new Comment(names[r.nextInt(names.length)], comment[r.nextInt(comment.length)], r.nextInt(2000), r.nextInt(3000)));
        }
    }


}