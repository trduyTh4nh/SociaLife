package com.example.projectmain;

import static android.view.View.inflate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectmain.Adapter.CommentAdapter;
import com.example.projectmain.Class.Commentclass;

import java.util.ArrayList;
import java.util.Random;


public class PostInfoActivity extends AppCompatActivity {
    EditText edtComment;
    TextView tvname, tvUsername, tvContent;
    ImageView ivPfp, ivImg;
    ImageButton btnExit;
    ArrayList<Commentclass> cmtList;
    RecyclerView rcvComment;
    LinearLayout llPostContain;
    View post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        int viewType = b.getInt("ViewType");
        /*
        * Các kiểu view:
         * 0: Post có hình nhưng ko caption
         * 1: Post có caption ngắn
         * 2: Post có caption dài
         * 3: Post vừa có hình, vừa có caption
         *
        */
        if(viewType == 0){
            //Né tránh nullPointerException cho Type 0: Chỉ setImageResource cho hình
            post = getLayoutInflater().inflate(R.layout.post_img_notext, null); //Lưu ý biến này: Chuẩn bị view để thêm vào LinearLayout.
            PrepareData();
            ivImg.setImageResource(b.getInt("Img"));

        } else if(viewType == 1){
            //Né tránh nullPointerException cho Type 1: Chỉ setText cho chữ
            post = getLayoutInflater().inflate(R.layout.post_small_paragraph, null); //Lưu ý biến này: Chuẩn bị view để thêm vào LinearLayout.
            PrepareData();
            tvContent.setText(b.getString("Content"));
        } else if(viewType == 3){
            //Né tránh nullPointerException cho Type 3: Không có nullPointerException, set hình và text bình thường
            post = getLayoutInflater().inflate(R.layout.post, null); //Lưu ý biến này: Chuẩn bị view để thêm vào LinearLayout.
            PrepareData();
            tvContent.setText(b.getString("Content"));
            tvContent.setText(b.getString("Content"));
            ivImg.setImageResource(b.getInt("Img"));
        } else if(viewType == 2){
            //Né tránh nullPointerException cho Type 2: Chỉ setText cho chữ
            post = getLayoutInflater().inflate(R.layout.post_large_paragraph, null); //Lưu ý biến này: Chuẩn bị view để thêm vào LinearLayout.
            PrepareData();
            tvContent.setText(b.getString("Content"));
        }
        llPostContain.addView(post); //Lưu ý hàm này: thêm View vừa mới chuẩn bị vào LinearLayout
        tvname.setText(b.getString("Name"));
        tvUsername.setText(b.getString("Username"));
        ivPfp.setImageResource(b.getInt("Pfp"));
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
        PrepareAdapter();
        rcvComment = findViewById(R.id.rcvComments);
        CommentAdapter cmtAdap = new CommentAdapter(this, cmtList);
        rcvComment.setAdapter(cmtAdap);
        rcvComment.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

    }
    void PrepareData(){
        //LƯU ÝÝÝÝÝÝÝÝÝÝ: CHỈ Chạy hàm này sau post = getLayoutInflater().inflate(R.layout.post_large_paragraph, null); (dòng 53, 59, 64, 71)
        llPostContain = findViewById(R.id.llPostContainer);
        tvname = post.findViewById(R.id.tvPName); //LƯU Ý: post.findViewById là lấy từ id của post vừa mới chuẩn bị khi nãy (các thao tác chuẩn bị ở dòng 53, 59, 64, 71)
        tvUsername = post.findViewById(R.id.tvUsername);
        ivPfp = post.findViewById(R.id.ivPfp);
        ivImg = post.findViewById(R.id.img_post);
        tvContent = post.findViewById(R.id.tvContent);
        btnExit = findViewById(R.id.btnHome);
        edtComment = findViewById(R.id.edtComment);

    }
    void PrepareAdapter(){
        String[] names = {"@elonmusk", "@denvau", "@wuang", "@sugar", "@31.03", "@khangZata"};
        String[] comment = {"Hello hello hi hi", "ok!", "bài đăng hay", "tao random", "ứng dụng rác, vứt đi"};
        Random r = new Random();
        for(int i = 0; i < 20; i++){
            cmtList.add(new Commentclass(names[r.nextInt(names.length)], comment[r.nextInt(comment.length)], r.nextInt(2000), r.nextInt(3000)));
        }
    }
}