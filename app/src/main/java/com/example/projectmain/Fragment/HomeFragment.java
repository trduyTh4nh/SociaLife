package com.example.projectmain.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.PerformanceHintManager;
import android.preference.PreferenceManager;
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

        List<Post> posts = db.getPost("file:///data/user/0/com.example.projectmain/cache/cropped577689494.jpg", "test cứng", "cứng ngắt");

        btnMenu = viewPost.findViewById(R.id.btnOptions);
        recyclerView = view.findViewById(R.id.render);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(getContext(), posts);

        recyclerView.setAdapter(adapter);

    }


//    public List<Post> listPost() {
//        List<Post> posts = new ArrayList<>();
//        posts.add(new Post(R.drawable.avatarvau, R.drawable.img_denvau, "@den:", "Đen Vâu", "1.1k", "nhạc anh bao cháy dìa dia", "1 ngày trước"));
//        return posts;
//    }
}