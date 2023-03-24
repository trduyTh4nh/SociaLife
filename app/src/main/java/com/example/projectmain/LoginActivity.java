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
import com.example.projectmain.Model.User;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{
    Button mBtnRegister;
    LinearLayout mBtnLogin;
    ImageButton mBtnLoginSmall;
    EditText edtEmail, edtPassword;
    ImageButton btnLogin;
    User user;


    DB db;
    SharedPreferences sharedPreferences;
    private  static final String SHARED_PRE_NAME = "mypref";
    private static final  String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
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

        user = new User();

        sharedPreferences = getSharedPreferences(SHARED_PRE_NAME, MODE_PRIVATE);

        String name = sharedPreferences.getString(KEY_NAME, null);

        if(name != null)
            SwitchActivity(HomeActivity.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if(edtEmail.equals("") || edtPassword.equals("")){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập thông tin đầy đủ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkUser = db.CheckEmailPassword(email, password);
                    if(checkUser){
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        user = db.getUser(email);

                        editor.putString(KEY_NAME, user.getName());
                        editor.putString(KEY_EMAIL, edtEmail.getText().toString());
                        editor.putString(KEY_PASSWORD, edtPassword.getText().toString());
                        editor.apply();

                        SwitchActivity(HomeActivity.class);
                    }
                    else
                        Toast.makeText(LoginActivity.this, "tài khoản không tồn tại hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

//    private SharedPreferences getSharedPreferences(String keyName, Integer integer) {
//
//    }

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