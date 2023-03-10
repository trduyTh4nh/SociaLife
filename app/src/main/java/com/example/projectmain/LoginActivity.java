package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText medtUsername, medtPassword;
    Button mbtnLogin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        medtUsername = (EditText) findViewById(R.id.edtUsername);
        medtPassword = (EditText) findViewById(R.id.edtPassword);
        mbtnLogin = (Button) findViewById(R.id.fbLogin);
        DB = new DBHelper(this);

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = medtUsername.getText().toString();
                String pass = medtPassword.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.CheckUsernamePassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void tvCreate_onClick(View view) {
        Intent intent  = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}