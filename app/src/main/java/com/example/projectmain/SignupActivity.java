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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmail, edtUserName, edtPassword, edtConfirm;
    LinearLayout btnSignup;
    //    LinearLayout mBtnRegis;
    ImageButton btnSignupSmall;
//    EditText mEdtDummy;
//    String UsrName;
//    String Psswd;
//    String Email;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // init
        initView();
        // handle
        db = new DB(this);
        btnSignup.setOnClickListener(this);
        btnSignupSmall.setOnClickListener(this);
//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = edtEmail.getText().toString();
//                String userName = edtUserName.getText().toString();
//                String password = edtPassword.getText().toString();
//                String confirmPass = edtConfirm.getText().toString();
////                 check lỗi khi nhập
//                if (email.equals("") || userName.equals("") || password.equals("") || confirmPass.equals("")) {
//                    Toast.makeText(SignupActivity.this, "Vui lòng diền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    if (password.equals(confirmPass)) {
//                        Boolean checkEmail = db.checkEmail(email);
//                        if (checkEmail == false) {
//
//                            Boolean insert = db.insertData(email, userName, password);
//                            if (insert == true) {
//                                Toast.makeText(SignupActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//                                startActivity(intent);
//                            } else
//                                Toast.makeText(SignupActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
//                        } else
//                            Toast.makeText(SignupActivity.this, "Tài khoản này đã được tạo", Toast.LENGTH_SHORT).show();
//                    } else
//                        Toast.makeText(SignupActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

    }

    @SuppressLint("WrongViewCast")
    public void initView() {
        edtEmail = findViewById(R.id.email_account);
        edtUserName = findViewById(R.id.userName_account);
        edtPassword = findViewById(R.id.pasword_account);
        edtConfirm = findViewById(R.id.confirm_account);
        btnSignup = findViewById(R.id.btnRegis);
        btnSignupSmall = findViewById(R.id.btnLoginSmall);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "register", Toast.LENGTH_SHORT).show();
    }
}


