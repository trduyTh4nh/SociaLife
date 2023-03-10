package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    EditText medtUsername, medtPassword, medtConfirmPassword;
    Button mbtnSignup , mbtnSignin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        medtUsername = (EditText) findViewById(R.id.edtUsername);
        medtPassword = (EditText) findViewById(R.id.edtPassword);
        medtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        mbtnSignup = (Button) findViewById(R.id.fbLogin);
        DB = new DBHelper(this);

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