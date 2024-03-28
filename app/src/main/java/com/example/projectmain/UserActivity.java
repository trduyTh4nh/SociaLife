package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectmain.Adapter.UserPostAdapter;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Follower;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.Refactoring.Proxy.UserManager;
import com.example.projectmain.Refactoring.Proxy.UserProxy;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    Button btnFollow, btnUnfollow;
    ImageButton btnExit;
    TextView mtvUsername, mtvFollowingCount, mtvFollowerCount, mtvPostCount, mtvDes;
    DB db;
    User user;
    ImageView avatarMain;
    ArrayList<Post> posts;
    String name;
    TabLayout tlPostType;
    ViewPager2 vp;
    SharedPreferences share;
    int id;
    int curID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        db = new DB(this);
        curID = GlobalUser.getInstance(this).getUser().getId();
        prepareView();
        Intent i = getIntent();
        Bundle b = i.getExtras();
        id = b.getInt("idUser");
        user = db.getUser(id);
        mtvUsername.setText(user.getName());
        if(curID == id){
            btnFollow.setVisibility(View.GONE);
            btnUnfollow.setVisibility(View.VISIBLE);
            btnUnfollow.setText("Đã đăng nhập bằng người dùng này.");
            btnUnfollow.setEnabled(false);
        }
        if(checkFollowed(curID, id)){
            btnFollow.setVisibility(View.GONE);
            btnUnfollow.setVisibility(View.VISIBLE);
        }
        if(user.getDescription() != null){
            mtvDes.setText(user.getDescription());
        }
        if(db.getImagefor(id) == null){
            avatarMain.setImageResource(R.drawable.def);
        } else
            avatarMain.setImageURI(Uri.parse(db.getImagefor(id)));
        mtvFollowingCount.setText(String.valueOf(listFollowers(id)));
        mtvFollowerCount.setText(String.valueOf(listFollow(id)));
        posts = new ArrayList<Post>();
        ListImgPost();
        UserPostAdapter adap = new UserPostAdapter(this,posts,this);
        vp.setAdapter(adap);
        new TabLayoutMediator(tlPostType, vp, ((tab, position) -> {
            if(position == 0){
                tab.setIcon(R.drawable.mountain_sun_solid);
            } else {
                tab.setIcon(R.drawable.font_solid);
            }
        })).attach();
        mtvPostCount.setText(String.valueOf(posts.size()));
        btnFollow.setOnClickListener(v -> Follow());
        btnUnfollow.setOnClickListener(v -> Unfollow());
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    void prepareView(){
        mtvUsername = findViewById(R.id.tvName);
        mtvDes = findViewById(R.id.tvDes);
        mtvFollowerCount = findViewById(R.id.tvFollowerCount);
        mtvFollowingCount = findViewById(R.id.tvFollowing);
        mtvPostCount = findViewById(R.id.tvPostCount);
        avatarMain = findViewById(R.id.avatar_main);
        tlPostType = findViewById(R.id.tlPostType);
        vp = findViewById(R.id.vpPosts);
        btnFollow = findViewById(R.id.btnFollow);
        btnUnfollow = findViewById(R.id.btnUnfollow);
        btnExit = findViewById(R.id.btn_exit);
    }
    public int listFollowers(int idCurrentUser) {
        List<Follower> list = new ArrayList<>();
        SQLiteDatabase database = db.getWritableDatabase();

        Cursor cursor = database.query("follower", null, "iduser = ?", new String[]{String.valueOf(idCurrentUser)}, null,null,null);

        return cursor.getCount();
    }
    public int listFollow(int idCurrentUser) {
        List<Follower> list = new ArrayList<>();
        SQLiteDatabase database = db.getWritableDatabase();

        Cursor cursor = database.query("follower", null, "idfollowing = ?", new String[]{String.valueOf(idCurrentUser)}, null,null,null);
        return cursor.getCount();
    }
    public Boolean checkFollowed(int id, int idFlo){
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor cursor = database.query("follower", null, "iduser = ? AND idfollowing = ?", new String[]{String.valueOf(id), String.valueOf(idFlo)}, null,null,null);
        return cursor.getCount() > 0;
    }
    @SuppressLint("Range")
    public void ListImgPost() {

        Cursor c = db.getPostsFromUser(id);
        while(c.moveToNext()){
            if(c.getInt(c.getColumnIndex("isshare")) == 1){
                continue;
            }
            Post temp = new Post();
            temp.setId(c.getInt(c.getColumnIndex("id")));
            temp.setIduser(id);
            temp.setImgPost(c.getString(3));
            temp.setContent(c.getString(c.getColumnIndex("content")));
            temp.setNumber_like(c.getString(c.getColumnIndex("like_count")));
            temp.setTime(c.getString(c.getColumnIndex("datetime")));
            posts.add(temp);
        }
    }
    public void Follow(){
        db.insertDataFollow(curID, id);
        mtvFollowerCount.setText(String.valueOf(listFollow(id)));
        btnFollow.setVisibility(View.GONE);
        btnUnfollow.setVisibility(View.VISIBLE);
    }
    public void Unfollow(){
        android.app.AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Hủy theo dõi");
        b.setMessage("Bạn có muốn hủy theo dõi người dùng " + mtvUsername.getText().toString() + "? Bạn sẽ không thể thấy thông báo khi họ đăng bài.");
        b.setPositiveButton("Hủy theo dõi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SQLiteDatabase database = db.getWritableDatabase();
                database.delete("follower", "iduser = ? and idfollowing = ?", new String[]{String.valueOf(curID), String.valueOf(id)});
                mtvFollowerCount.setText(String.valueOf(listFollow(id)));
                btnFollow.setVisibility(View.VISIBLE);
                btnUnfollow.setVisibility(View.GONE);
            }
        });
        b.setNegativeButton("Hủy", null);
        AlertDialog a = b.create();
        a.show();

    }
}