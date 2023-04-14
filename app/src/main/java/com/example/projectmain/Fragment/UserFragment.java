package com.example.projectmain.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectmain.Adapter.ImageAdapter;
import com.example.projectmain.Class.ImageClass;
import com.example.projectmain.Database.DB;
import com.example.projectmain.R;
import com.google.android.material.navigation.NavigationView;

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
    TextView fmTvPostCount;
    int[] imageRes = {R.drawable.imgquang, R.drawable.meo, R.drawable.imgcrew, R.drawable.imgpc};
    private ArrayList<ImageClass> list = new ArrayList<ImageClass>();

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;
    TextView mtvUsername,mtvFollowingCount;
    Button mbtnLogout;
    DB db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random r = new Random();
        for(int i = 1; i <= 20; i++){
            list.add(new ImageClass(imageRes[r.nextInt(imageRes.length)]));
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageAdapter adapter = new ImageAdapter(getActivity().getApplicationContext(), list);
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
        fmTvPostCount = getView().findViewById(R.id.tvPostCount);        fmTvPostCount = getView().findViewById(R.id.tvPostCount);
        fmTvPostCount.setText(String.valueOf(adapter.getItemCount()));

//        mbtnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.clear();
//                editor.commit();
//                getActivity().finish();
//            }
//        });
    }
}