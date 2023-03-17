package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectmain.Database.DB;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{
    Button mBtnRegister;
    LinearLayout mBtnLogin;
    ImageButton mBtnLoginSmall;
    EditText edtEmail, edtPassword;
    ImageButton btnLogin;


    DB db;
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnRegister:
                SwitchActivity(SignupActivity.class);
                break;
            default:
                SwitchActivity(HomeActivity.class);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        // handle
        db = new DB(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtEmail.getText().toString();
                SwitchActivity(HomeActivity.class);
                // check
//                if(email.equals("") || password.equals("")){
//                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Boolean checkEmailandPass = db.CheckEmailAndPassword(email, password);
//                    if(checkEmailandPass == true)
//                    {
//                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                    }
//                    else {
//                        Toast.makeText(LoginActivity.this, "Sai thông tin tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
//                        edtEmail.setText("");
//                        edtPassword.setText("");
//                    }
//                }
            }
        });

    }

    @SuppressLint("WrongViewCast")
    public void initView(){
        edtEmail = findViewById(R.id.email_login);
        edtPassword = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btnLogin);
    }

    // switch activity
    private void SwitchActivity(Class<?> a){
        Intent i = new Intent(this, a);
        startActivity(i);
    }
}