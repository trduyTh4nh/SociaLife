package com.example.projectmain.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(getContext(), listPost());

        recyclerView.setAdapter(adapter);

    }


    public List<Post> listPost() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(R.drawable.avatarquang, R.drawable.imgquang, "@wuang:", "Trí Quang", "1.1m", "đặc cầu tulen", "30 phút trước"));
        posts.add(new Post(R.drawable.avatarduong, R.drawable.imgduong, "@Sugar:", "Gia Đường", "1.8m", "hello hi hi", "vài phút trước"));
        posts.add(new Post(R.drawable.avatarvau, R.drawable.img_denvau, "@den:", "Đen Vâu", "1.1k", "nhạc anh bao cháy dìa dia", "1 ngày trước"));
        posts.add(new Post(R.drawable.avatarquang, R.drawable.imgquang, "@wuang:", "Trí Quang", "1.1m", "đặc cầu tulen", "30 phút trước"));
        posts.add(new Post(R.drawable.avatarduong, R.drawable.imgduong, "@Sugar:", "Gia Đường", "1.8m", "hello hi hi", "vài phút trước"));
        posts.add(new Post(R.drawable.avatarvau, R.drawable.img_denvau, "@den:", "Đen Vâu", "1.1k", "nhạc anh bao cháy dìa dia", "1 ngày trước"));
        return posts;
    }
}