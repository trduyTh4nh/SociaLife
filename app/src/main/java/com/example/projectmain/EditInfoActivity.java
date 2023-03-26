package com.example.projectmain;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.User;

public class EditInfoActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtUserName, edtPassword, edtConfirmPass, edtStory;
    ImageView imgCurrent;
    Button btnSave;
    ImageButton btnChangeImage;
    DB db;
    SharedPreferences sharedPreferences;

    User user;

    Uri uriImage;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    private static final String KEY_PASSWORD = "password";

    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NAME = "name";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initView();



        db = new DB(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
//        String newName = edtName.getText().toString();

        String userName = edtUserName.getText().toString();
        String story = edtStory.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPass = edtConfirmPass.getText().toString();



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!edtPassword.getText().toString().equals("") || !edtConfirmPass.getText().toString().equals("")){
                    if(edtPassword.getText().toString().equals(edtConfirmPass.getText().toString())){
                        Boolean updateInfo = db.UpdateDataEditInfo(KEY_EMAIL,edtUserName.getText().toString() , story);
                        if(updateInfo){
                            SharedPreferences.Editor editor =  sharedPreferences.edit();

                            editor.putString(KEY_NAME, edtUserName.getText().toString());
                            editor.putString(KEY_DESCRIPTION, edtStory.getText().toString());
                            editor.apply();
                            Toast.makeText(EditInfoActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(EditInfoActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EditInfoActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(EditInfoActivity.this, "Vui lòng nhập password để xác nhận", Toast.LENGTH_SHORT).show();
                }
            }
        });



        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String name = sharedPreferences.getString(KEY_NAME, null);
        String desc = sharedPreferences.getString(KEY_DESCRIPTION, null);
        String pass = sharedPreferences.getString(KEY_PASSWORD, null);


        user = db.getUser(email);


    }
     private void initView(){
         imgCurrent = findViewById(R.id.avart_current);
         btnSave = findViewById(R.id.btnSave);
         btnChangeImage = findViewById(R.id.btnEdit_image);
         edtUserName = findViewById(R.id.edt_username);
         edtPassword = findViewById(R.id.edt_password);
         edtConfirmPass = findViewById(R.id.edt_confirmPass);
         edtStory = findViewById(R.id.edt_story);
     }
}