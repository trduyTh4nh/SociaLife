package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    EditText medtUsername, medtPassword, medtConfirmPassword;
    Button mbtnSignup , mbtnSignin;
    DBHelper DB;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        medtUsername = (EditText) findViewById(R.id.edtUsername);
        medtPassword = (EditText) findViewById(R.id.edtPassword);
        medtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        mbtnSignup = (Button) findViewById(R.id.fbLogin);
        DB = new DBHelper(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        //Kiểm tra share preference có tồn tại hay không?

        String name = sharedPreferences.getString(KEY_NAME,null);

        if (name != null){
            Intent i = new Intent(SignupActivity.this , HomeActivity.class);
            startActivity(i);
        }

        mbtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = medtUsername.getText().toString();
                String pass = medtPassword.getText().toString();
                String repass = medtConfirmPassword.getText().toString();


                //Kiểm tra lỗ trống của 3 Edt
                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = DB.CheckUsername(user);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(user, pass);
                            if(insert==true){
                                //Share Preference
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_NAME,medtUsername.getText().toString());
                                editor.putString(KEY_PASSWORD,medtPassword.getText().toString());
                                editor.apply();

                                Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignupActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignupActivity.this, "Tài khoản này đã được tạo !", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignupActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }
}