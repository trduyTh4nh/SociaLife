package com.example.projectmain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this, "Home page", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Search user", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_add:
                        Toast.makeText(MainActivity.this, "Add post", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_notify:
                        Toast.makeText(MainActivity.this, "Notification layout", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_user:
                        Toast.makeText(MainActivity.this, "User profile", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

}