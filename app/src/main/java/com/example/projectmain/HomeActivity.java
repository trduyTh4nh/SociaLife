package com.example.projectmain;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmain.Fragment.AddFragment;
import com.example.projectmain.Fragment.HomeFragment;
import com.example.projectmain.Fragment.InfoUserFragment;
import com.example.projectmain.Fragment.NotifyFragment;
import com.example.projectmain.Fragment.SreachFragment;
import com.example.projectmain.Fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    @Override //ngăn việc quy lại trang đăng nhập
    public void onBackPressed() {
    }

    BottomNavigationView navigationView;
    FloatingActionButton btnAdd;
    TextView test;

    UserFragment uf;
    Toolbar toolbar_main;

    FragmentManager Fmanager = getSupportFragmentManager();

    ImageButton bars;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);

        Fmanager = getSupportFragmentManager();

        bars = findViewById(R.id.btnSetting);


        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        btnAdd = findViewById(R.id.fab);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(i);
            }
        });
        bars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSetting = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(iSetting);
            }
        });


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                Boolean isAdd = false;

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        isAdd = false;
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_search:
                        isAdd = false;
                        fragment = new SreachFragment();
                        break;
                    case R.id.nav_add:
                        isAdd = true;
                        Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_notify:
                        isAdd = false;
                        fragment = new NotifyFragment();
                        break;
                    case R.id.nav_user:
                        isAdd = false;
                        fragment = new UserFragment();
                        break;
                }
                if(!isAdd){
                    getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                }
                return true;
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_search:
                fragment = new SreachFragment();
                break;
//            case R.id.nav_add:
//                fragment = new AddFragment();
//                break;
            case R.id.nav_notify:
                fragment = new NotifyFragment();
                break;
            case R.id.nav_user:
                fragment = new UserFragment();
                break;
            default:
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
        return true;
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_option_post, menu);
    }
}