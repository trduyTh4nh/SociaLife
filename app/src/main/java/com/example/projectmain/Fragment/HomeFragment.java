package com.example.projectmain.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.PerformanceHintManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.projectmain.Adapter.PostAdapter;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;
import com.google.android.material.internal.CollapsingTextHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView recyclerView;
    PostAdapter adapter;

    DB db;

    User user;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";

    private static final String KEY_EMAIL = "email";

    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE_LINK = "linkImage";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    List<String> listName;
    ImageButton btnHeart;
    ImageButton btnMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View viewPost = getLayoutInflater().inflate(R.layout.post, null);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String linkImage = sharedPreferences.getString(KEY_IMAGE_LINK, null);

        db = new DB(getContext().getApplicationContext());

        user = db.getUser(email);
        listName = db.getListNameID();

        SQLiteDatabase database = db.getReadableDatabase();

//        Cursor cursorGetUser = database.rawQuery("SELECT u.* FROM user u JOIN post p on u.id = p.iduser", null);
//        List<Integer> list = new ArrayList<Integer>();
//
//        List<Post> posts = null;
//        while (cursorGetUser.moveToNext()) {
//
//            int idfit = cursorGetUser.getInt(0);
//            list.add(idfit);
//            String userName = cursorGetUser.getString(1);
//
//
//            //  Log.d("value is", listName.get(idfit));
//            // if(idfit == db.getIdUserPost(user.getId())){
//
//            //   }
//
//            //Index: 11, Size: 7
//        }
        List<Post> posts = null;
        for (int i = 0; i < listName.size(); i++) {
            posts = getPost("file:///data/user/0/com.example.projectmain/cache/cropped1112244420.jpg", listName.get(i), "cứng");

        }
        adapter = new PostAdapter(getContext().getApplicationContext(), posts);


        btnMenu = viewPost.findViewById(R.id.btnOptions);
        recyclerView = view.findViewById(R.id.render);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(adapter);

    }

    public List<Post> getPost(String avatar, String userName, String name) {
        String[] column = {"content", "image", "comment_count", "datetime"};
        List<Post> posts = new ArrayList<Post>();
        SQLiteDatabase myDB = db.getWritableDatabase();

        Cursor cursor = myDB.query("post", null, null, null, null, null, null);

        //Cursor cursorGetUser = myDB.rawQuery("SELECT u.* FROM user u JOIN post p on u.id = p.iduser", null);

        while (cursor.moveToNext()) {
            int iduser = cursor.getInt(1);
            String content = cursor.getString(2);
            String img = cursor.getString(3);
            int count_like = cursor.getInt(4);
            int count_comment = cursor.getInt(5);
            int count_share = cursor.getInt(6);
            String time = cursor.getString(7);

            posts.add(new Post(iduser, avatar, img, userName, name, String.valueOf(count_like), content, time));

        }
        return posts;
    }


//    public List<Post> listPost() {
//        List<Post> posts = new ArrayList<>();
//        posts.add(new Post(R.drawable.avatarvau, R.drawable.img_denvau, "@den:", "Đen Vâu", "1.1k", "nhạc anh bao cháy dìa dia", "1 ngày trước"));
//        return posts;
//    }
}