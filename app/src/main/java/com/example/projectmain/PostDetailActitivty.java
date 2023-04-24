package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmain.Adapter.CommentAdapter;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Comment;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostDetailActitivty extends AppCompatActivity {

    CheckBox btnLike;
    EditText edtComment;
    TextView tvname, tvUsername, tvContent, tvTime, numberLike;
    View postView;
    ImageView ivPfp, ivImg;
    ImageButton btnExit;
    Post post;
    Button btnUpcmt, btnFollow;
    ArrayList<Comment> cmtList;
    ImageButton btnMore;
    RecyclerView rcvComment;
    DB db;
    User user;
    SharedPreferences sharedPreferences;
    LinearLayout llPostContain, btnUser;
    int id;
    private static final String SHARED_PREF_NAME = "mypref";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMAGE_LINK = "linkImage";

    private static final String KEY_NAME = "name";

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_actitivty);
        // init
        // handle

        db = new DB(this);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        id = b.getInt("idUser");
        int viewType = b.getInt("ViewType");

        /*
         * Các kiểu view:
         * 0: Post có hình nhưng ko caption
         * 1: Post có caption ngắn
         * 2: Post có caption dài
         * 3: Post vừa có hình, vừa có caption
         *
         */
        if (viewType == 0) {
            //Né tránh nullPointerException cho Type 0: Chỉ setImageResource cho hình
            postView = getLayoutInflater().inflate(R.layout.post_img_notext, null); //Lưu ý biến này: Chuẩn bị view để thêm vào LinearLayout.
            initView();
            ivImg.setImageURI(Uri.parse(b.getString("Img")));

        } else if (viewType == 1) {
            //Né tránh nullPointerException cho Type 1: Chỉ setText cho chữ
            postView = getLayoutInflater().inflate(R.layout.post_small_paragraph, null); //Lưu ý biến này: Chuẩn bị view để thêm vào LinearLayout.
            initView();
            tvContent.setText(b.getString("Content"));
        } else if (viewType == 3) {
            //Né tránh nullPointerException cho Type 3: Không có nullPointerException, set hình và text bình thường
            postView = getLayoutInflater().inflate(R.layout.post, null); //Lưu ý biến này: Chuẩn bị view để thêm vào LinearLayout.
            initView();
            tvContent.setText(b.getString("Content"));
            tvContent.setText(b.getString("Content"));
            ivImg.setImageURI(Uri.parse(b.getString("Img")));
        } else if (viewType == 2) {
            //Né tránh nullPointerException cho Type 2: Chỉ setText cho chữ
            postView = getLayoutInflater().inflate(R.layout.post_large_paragraph, null); //Lưu ý biến này: Chuẩn bị view để thêm vào LinearLayout.
            initView();
            tvContent.setText(b.getString("Content"));
        }

        llPostContain.addView(postView); //Lưu ý hàm này: thêm View vừa mới chuẩn bị vào LinearLayout

        tvname.setText(b.getString("Name"));
        tvUsername.setText(b.getString("Username"));
        String userName = b.getString("Username");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        String test = b.getString("Img");
        String linkImage = b.getString("Pfp");
        String time = b.getString("Time");
        tvTime.setText(time);
        ivPfp.setImageURI(Uri.parse(linkImage));
        int idUser = db.getIduser(name);
        int idPost = b.getInt("idPost");
        numberLike.setText(String.valueOf(db.getLike(idPost).getCount()));
        if (!db.CheckLike(idUser, idPost)) {
            btnLike.setChecked(false);
            btnLike.setBackgroundResource(R.drawable.favorite_svgrepo_com);
        } else {
            btnLike.setChecked(true);
            btnLike.setBackgroundResource(R.drawable.outline_favorite_24);
        }
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (!b.getBoolean("IsCmt")) {
            edtComment.requestFocus();
        }
        cmtList = (ArrayList<Comment>) getCmt(idPost);
        rcvComment = findViewById(R.id.rcvComments);
        CommentAdapter cmtAdap = new CommentAdapter(cmtList, getApplicationContext());
        rcvComment.setAdapter(cmtAdap);
        cmtAdap.notifyItemInserted(0);
        rcvComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        btnUpcmt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String name = db.getName(idUser);

                SQLiteDatabase database = db.getReadableDatabase();
                String contentCmt = edtComment.getText().toString();
                if (!contentCmt.equals("")) {
                    db.insertComment(idUser, idPost, contentCmt);
                    Toast.makeText(PostDetailActitivty.this, "Đã bình luận", Toast.LENGTH_SHORT).show();
                } else {
                    edtComment.requestFocus();
                    Toast.makeText(PostDetailActitivty.this, "Bạn chưa nhập nội dung", Toast.LENGTH_SHORT).show();
                }
                cmtList.clear();
                cmtList.addAll((ArrayList<Comment>) getCmt(idPost));
                cmtAdap.notifyDataSetChanged();
            }
        });
        btnFollow.setVisibility(View.GONE);
        btnMore.setVisibility(View.GONE);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostDetailActitivty.this, UserActivity.class);
                Bundle b = new Bundle();
                b.putInt("idUser", id);
                i.putExtras(b);
                startActivity(i);
            }
        });

        btnLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!db.CheckLike(idUser, id)) {
                    Boolean insertLike = db.insertLikes(idUser, id);
                    if (insertLike && b) {
                        btnLike.setBackgroundResource(R.drawable.outline_favorite_24);
                        numberLike.setText(String.valueOf(db.getLike(idPost).getCount()));
                    }
                } else {
                    db.Unlike(idUser, id);
                    btnLike.setBackgroundResource(R.drawable.favorite_svgrepo_com);
                    numberLike.setText(String.valueOf(db.getLike(idPost).getCount()));
                }
            }
        });
    }
    void initView() {
       /* tvname = findViewById(R.id.tvPName);
        tvUsername = findViewById(R.id.tvUsername);
        ivPfp = findViewById(R.id.ivPfp);
        ivImg = findViewById(R.id.img_post);
        btnExit = findViewById(R.id.btnExit);
        edtComment = findViewById(R.id.edtComment);
        btnUpcmt = findViewById(R.id.btnUploadComment);*/
        llPostContain = findViewById(R.id.llPostContainer);
        tvname = postView.findViewById(R.id.name); //LƯU Ý: post.findViewById là lấy từ id của post vừa mới chuẩn bị khi nãy (các thao tác chuẩn bị ở dòng 53, 59, 64, 71)
        tvUsername = postView.findViewById(R.id.nameu_user);
        ivPfp = postView.findViewById(R.id.avatar);
        ivImg = postView.findViewById(R.id.img_post);
        btnFollow = postView.findViewById(R.id.btnFlolow);
        btnMore = postView.findViewById(R.id.btnOptions);
        tvContent = postView.findViewById(R.id.content_post);
        btnUser = postView.findViewById(R.id.btnShowProfile);
        btnExit = findViewById(R.id.btnExit);
        edtComment = findViewById(R.id.edtComment);
        btnUpcmt = findViewById(R.id.btnUploadComment);
        tvTime = postView.findViewById(R.id.time_post);
            btnLike = postView.findViewById(R.id.btn_like);
            numberLike = postView.findViewById(R.id.number_like);
    }


    public List<Comment> getCmt(int idPost) {
        String[] column = {"content", "image", "comment_count", "datetime"};
        List<Comment> cmt = new ArrayList<Comment>();
        SQLiteDatabase myDB = db.getWritableDatabase();

        Cursor cursor = myDB.query("comment", null, " idpost = ?", new String[]{String.valueOf(idPost)}, null, null, null);

      // Cursor cursor = myDB.rawQuery("SELECT * FROM commnent c  WHERE c.idpost = ? ", new String[]{String.valueOf(idPost)});

        while (cursor.moveToNext()) {
            int idUser = cursor.getInt(1);
            int idpost = cursor.getInt(2);
            String content = cursor.getString(3);
            String nameUserComment = db.getName(idUser);
            cmt.add(new Comment(nameUserComment, content, 0, 0));
        }
        cursor.close();
        return cmt;
    }


}