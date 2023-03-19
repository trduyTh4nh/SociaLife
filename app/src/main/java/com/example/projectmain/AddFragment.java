package com.example.projectmain;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.Manifest.permission;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmain.models.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class AddFragment extends Fragment {


    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }
    EditText medtNoiDung;
    TextView mtvName;
    Button mbtnDangBai,mbtnChooseImage;
    ImageView mimgDangBai;
    DBHelper DB;
    User user;
    SharedPreferences sharedPreferences;

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    String[]cameraPermission;
    String[]storagePermission;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        medtNoiDung = view.findViewById(R.id.edtNoiDung);
        mimgDangBai = view.findViewById(R.id.imgDangBai);
        mtvName = view.findViewById(R.id.tvName);
        mbtnDangBai = view.findViewById(R.id.btnDangBai);

        DB = new DBHelper(getActivity());
        user = new User();

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME,null);

        mtvName.setText(name);

        mbtnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddActivity.class);
                startActivity(intent);
            }
        });

    }

}