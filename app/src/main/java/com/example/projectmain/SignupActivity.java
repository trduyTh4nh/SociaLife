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

    DB db;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";

    private static  final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

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

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
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
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String user = edtUserName.getText().toString();
        String pass = edtPassword.getText().toString();
        String repass = edtConfirm.getText().toString();
        if(email.equals("")||pass.equals("")||repass.equals("")||user.equals(""))
            Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        else if (email.matches(emailPattern)) {
            //Check repass
            if(pass.equals(repass)){

                //check name
                Boolean checkname = db.CheckName(user);
                if(checkname == false){

                    //check email
                    Boolean checkemail = db.CheckEmail(email);
                    if(checkemail==false){

                        //Truyền dữ liệu
                        Boolean insert = db.insertUser(user);

                        int iduser = db.getIduser(user);

                        Boolean insert1 = db.insertData(iduser,email, pass);

                        if(insert==true){
                            //Share Preference
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString(KEY_EMAIL,edtEmail.getText().toString());
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
        else{
            Toast.makeText(SignupActivity.this, "Vui lòng nhập đúng email", Toast.LENGTH_SHORT).show();
        }

    }
}


