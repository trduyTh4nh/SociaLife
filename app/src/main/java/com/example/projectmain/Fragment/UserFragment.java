package com.example.projectmain.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectmain.Adapter.ImageAdapter;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Image;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;
import com.google.android.material.navigation.NavigationView;

import android.annotation.SuppressLint;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class UserFragment extends Fragment {


    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IMAGE_LINK = "linkImage";

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;
    TextView mtvUsername, mtvFollowingCount, mtvFollowerCount, mtvPostCount, mtvDes;
    Button mbtnLogout;
    DB db;
    User user;
    ImageView avatarMain;
    int[] imageRes = {R.drawable.imgquang, R.drawable.meo, R.drawable.imgcrew, R.drawable.imgpc};
    private ArrayList<Image> list = new ArrayList<Image>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random r = new Random();
        for (int i = 1; i <= 5; i++) {
            list.add(new Image(imageRes[r.nextInt(imageRes.length)]));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avatarMain = view.findViewById(R.id.avatar_main);
        mtvUsername = view.findViewById(R.id.tvName);
        mtvFollowingCount = view.findViewById(R.id.tvFollowing);
        mtvFollowerCount = view.findViewById(R.id.tvFollowerCount);
        mtvPostCount = view.findViewById(R.id.tvPostCount);
        mtvDes = view.findViewById(R.id.tvDes);

        db = new DB(getActivity());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


        String name = sharedPreferences.getString(KEY_NAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);

        String linkImage = sharedPreferences.getString(KEY_IMAGE_LINK, null);
        user = db.getUser(email);
        Uri link = Uri.parse(linkImage);
        mtvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext().getApplicationContext(), linkImage, Toast.LENGTH_SHORT).show();
            }
        });

        if (name != null) {
           avatarMain.setImageURI(link);
            mtvUsername.setText(user.getName());
            mtvDes.setText(user.getDescription());
            mtvPostCount.setText(String.valueOf(user.getPost_count()));
            mtvFollowingCount.setText(String.valueOf(user.getFollowing_count()));
            mtvFollowerCount.setText(String.valueOf(user.getFollower_count()));
        }

        ImageAdapter adapter = new ImageAdapter(list, getContext().getApplicationContext());
        RecyclerView r = getView().findViewById(R.id.rcvImages);
        r.setNestedScrollingEnabled(false);
        r.setAdapter(adapter);
        GridLayoutManager g = new GridLayoutManager(getActivity().getApplicationContext(), 3, GridLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        r.setLayoutManager(g);

    }

}