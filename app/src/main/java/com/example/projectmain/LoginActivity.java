package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText medtEmail, medtPassword;
    Button mbtnLogin;
    DBHelper DB;

    //Share Preferences
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        medtEmail = (EditText) findViewById(R.id.edtEmailLogin);
        medtPassword = (EditText) findViewById(R.id.edtPassword);
        mbtnLogin = (Button) findViewById(R.id.fbLogin);
        DB = new DBHelper(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        //Kiểm tra share preference có tồn tại hay không?

        String name = sharedPreferences.getString(KEY_NAME,null);

        if (name != null){
            Intent i = new Intent(LoginActivity.this , HomeActivity.class);
            startActivity(i);
        }

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = medtEmail.getText().toString();
                String pass = medtPassword.getText().toString();

                if(email.equals("")||pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.CheckEmailPassword(email, pass);
                    if(checkuserpass==true){

                        //Share Preference
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_NAME,medtEmail.getText().toString());
                        editor.putString(KEY_PASSWORD,medtPassword.getText().toString());
                        editor.apply();

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