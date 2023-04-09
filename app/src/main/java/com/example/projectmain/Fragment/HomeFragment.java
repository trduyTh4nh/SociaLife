package com.example.projectmain.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    ArrayList<Post> PostArrayList;
    DB db;


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

        btnMenu = viewPost.findViewById(R.id.btnOptions);
        recyclerView = view.findViewById(R.id.render);
        db = new DB(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(getContext(), listPost());

        recyclerView.setAdapter(adapter);

    }

    public List<Post> listPost() {
        List<Post> posts = new ArrayList<>();

        Cursor cursor = db.readAllData();
        Post post = new Post();
        while (cursor.moveToNext()) {
            post.setContent(cursor.getString(2));
            post.setIduser(Integer.parseInt(cursor.getString(1)));
            int iduser = post.getIduser();
            post.setUsername(String.valueOf(db.checkUsername(iduser)));
            posts.add(new Post(post.getIduser(), R.drawable.avatarquang, R.drawable.imgquang,post.getUsername(), "Trí Quang", "1.1m", post.getContent(), "30 phút trước"));
        }
        return posts;
    }



}