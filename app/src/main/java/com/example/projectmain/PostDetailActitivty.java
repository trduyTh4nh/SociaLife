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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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


    EditText edtComment;
    TextView tvname, tvUsername;
    ImageView ivPfp, ivImg;
    ImageButton btnExit;
    Post post;
    Button btnUpcmt;
    ArrayList<Comment> cmtList;
    RecyclerView rcvComment;
    DB db;
    User user;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";

    private static final String KEY_EMAIL = "email";

    private static final String KEY_NAME = "name";

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_actitivty);
        // init
        initView();
        // handle
        db = new DB(this);
        Intent i = getIntent();
        Bundle b = i.getExtras();


        tvname.setText(b.getString("Name"));
        tvUsername.setText(b.getString("Username"));
        ivPfp.setImageURI(Uri.parse(b.getString("Img")));
        ivImg.setImageURI(Uri.parse(b.getString("Img")));
        String userName = b.getString("Username");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        int idUser = db.getIduser(name);

        int idPost = b.getInt("idPost");

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




    }

    void initView() {
        tvname = findViewById(R.id.tvPName);
        tvUsername = findViewById(R.id.tvUsername);
        ivPfp = findViewById(R.id.ivPfp);
        ivImg = findViewById(R.id.img_post);
        btnExit = findViewById(R.id.btnExit);
        edtComment = findViewById(R.id.edtComment);
        btnUpcmt = findViewById(R.id.btnUploadComment);
    }

    //    void DataAdapter() {
//        String[] names = {"@elonmusk", "@denvau", "@wuang", "@sugar", "@31.03", "@khangZata"};
//        String[] comment = {"Hello hello hi hi", "ok!", "bài đăng hay", "tao random", "ứng dụng rác, vứt đi"};
//        Random r = new Random();
//
//        cmtList.add(new Comment(names[r.nextInt(names.length)], comment[r.nextInt(comment.length)], r.nextInt(2000), r.nextInt(3000)));
//
//    }
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