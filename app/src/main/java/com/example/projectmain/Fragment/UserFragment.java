package com.example.projectmain.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.google.android.material.snackbar.Snackbar;

import android.annotation.SuppressLint;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
//    int[] imageRes = {R.drawable.imgquang, R.drawable.meo, R.drawable.imgcrew, R.drawable.imgpc};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        for (int r : imageRes) {
//            list.add(new Image(r));
//        }

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
        Uri link;
        user = db.getUser(email);
        String strImageAvatar = db.getImagefor(user.getId());
//        if (linkImage == null) {
//            link = null;
//        } else
        link = Uri.parse(strImageAvatar);

        if (name != null) {
            if (link == null) {
                avatarMain.setImageResource(R.drawable.def);
            } else
                avatarMain.setImageURI(link);

            mtvUsername.setText(user.getName());
            mtvDes.setText(user.getDescription());
            mtvPostCount.setText(String.valueOf(CountPost(db.getIduser(name))));
            mtvFollowingCount.setText(String.valueOf(user.getFollowing_count()));
            mtvFollowerCount.setText(String.valueOf(user.getFollower_count()));
        }
        List<String> imageList = ListImgPost(db.getIduser(name));
        //   ArrayList<String> list = (ArrayList<String>) ListImgPost(db.getIduser(name));
        mtvPostCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < imageList.size(); i++) {
                    Log.d("Images: ", imageList.get(i));
                }
            }

        });

        ImageAdapter adapter = new ImageAdapter(imageList, getContext().getApplicationContext());
        RecyclerView r = getView().findViewById(R.id.rcvImages);
        r.setNestedScrollingEnabled(false);
        r.setAdapter(adapter);
        GridLayoutManager g = new GridLayoutManager(getActivity().getApplicationContext(), 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        r.setLayoutManager(g);


    }

    @Override
    public void onResume() {
        super.onResume();
        String strImageAvatar = db.getImagefor(user.getId());
//        if (linkImage == null) {
//            link = null;
//        } else
       Uri link = Uri.parse(strImageAvatar);
        avatarMain.setImageURI(link);
    }

    public int CountPost(int idUser) {
        SQLiteDatabase database = db.getReadableDatabase();

        Cursor cursorCount = database.query("post", null, "idUser = ?", new String[]{String.valueOf(idUser)}, null, null, null);
        int count = 0;
        while (cursorCount.moveToNext()) {
            count++;
        }

        return count;
    }

    @SuppressLint("Range")
    public List<String> ListImgPost(int idUser) {
        String[] column = {"image"};
        List<String> listImg = new ArrayList<String>();
        SQLiteDatabase database = db.getReadableDatabase();

        Cursor cursor = database.query("post", null, "idUser = ?", new String[]{String.valueOf(idUser)}, null, null, null);
        while (cursor.moveToNext()) {
            listImg.add(cursor.getString(3));
        }

        return Collections.singletonList(String.valueOf(listImg));
    }


}