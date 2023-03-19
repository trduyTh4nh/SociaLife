package com.example.projectmain;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    FloatingActionButton btnAdd;

    TextView test;
    EditText testedit   ;
    Toolbar toolbar, toolbar_main;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar_main = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);


        // this is line hide
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        btnAdd = findViewById(R.id.fab);
        btnAdd .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new AddFragment()).commit();
            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Activity activity = null;
                Fragment fragment = null;


                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_search:
                        fragment = new SreachFragment();
                        break;
                    case R.id.nav_add:
                        fragment = new AddFragment();
                        break;
                    case R.id.nav_notify:
                        fragment = new NotifyFragment();
                        break;
                    case R.id.nav_user:
                        fragment = new UserFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
                return true;
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.header, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        switch (id){
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_search:
                fragment = new SreachFragment();
                break;
            case R.id.nav_add:

                fragment = new AddFragment();
                break;
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
}