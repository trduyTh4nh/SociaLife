package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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

    DB DB;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // init
        initView();
        // handle
        DB = new DB(this);
        btnSignup.setOnClickListener(this);
        btnSignupSmall.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
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
        String email = edtEmail.getText().toString();
        String user = edtUserName.getText().toString();
        String pass = edtPassword.getText().toString();
        String repass = edtConfirm.getText().toString();
        if(email.equals("")||pass.equals("")||repass.equals("")||user.equals(""))
            Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        else{

            //Check repass
            if(pass.equals(repass)){

                //check name
                Boolean checkname = DB.CheckName(user);
                if(checkname == false){

                    //check email
                    Boolean checkemail = DB.CheckEmail(email);
                    if(checkemail==false){

                        //Truyền dữ liệu
                        Boolean insert = DB.insertUser(user);
                        int iduser = DB.getIduser(user);
                        Boolean insert1 = DB.insertData(iduser,email, pass);
                        if(insert==true){
                            //Share Preference
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_NAME,edtUserName.getText().toString());
                            editor.putString(KEY_PASSWORD,edtPassword.getText().toString());
                            editor.apply();
                            //

                            Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignupActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(SignupActivity.this, "Email này đã được tạo !", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SignupActivity.this, "Tên này đã được sử dụng !", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(SignupActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            }
        }

    }
}


