package com.example.projectmain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmain.models.User;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class AddActivity extends AppCompatActivity {
    EditText medtNoiDung;
    TextView mtvName;
    Button mbtnDangBai;
    ImageView mimgDangBai;
    DBHelper DB;
    User user;
    SharedPreferences sharedPreferences;


    //Uri của hình ảnh
    Uri imageUri;

    //Code này mình tự tạo số bao nhiêu cũng đc
    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    public static final int IMAGE_PICK_GALLERY = 102;
    public static final int IMAGE_PICK_CAMERA = 103;

    private String[]cameraPermission;
    private String[]storagePermission;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        medtNoiDung = findViewById(R.id.qwe);
        mimgDangBai = findViewById(R.id.asd);
        mtvName = findViewById(R.id.vbn);
        mbtnDangBai = findViewById(R.id.zxc);

        DB = new DBHelper(this);
        user = new User();

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME,null);

        mtvName.setText(name);
        mbtnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = medtNoiDung.getText().toString();

                if(content.equals(""))
                    Toast.makeText(AddActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                else{
                    int iduser = DB.getIduser(name);
                    SQLiteDatabase MyDB = DB.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("iduser", iduser);
                    contentValues.put("content", content);
                    contentValues.put("image", String.valueOf(imageUri));
                    long result = MyDB.insert("posts" , null, contentValues);
                    if(result>0){
                        Toast.makeText(AddActivity.this, "Đăng nội dung thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddActivity.this,HomeFragment.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AddActivity.this, "Đăng nội dung thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mimgDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePickDiaLlog();
            }
        });
    }

    //Tạo Dialog khi muốn thêm hình ảnh
    private void ImagePickDiaLlog(){
        //Sự lựa chọn giữa Camera và Thu viện
        String[] option = {"Camera","Thư viên"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn hình ảnh từ");

        //Chọn giữa Camera và Thư viện
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Vào Camera
                if(i == 0){

                    //Check xem điện thoại mình có đc allow Camera và thư viên không
                    //Nếu chưa có thì sẽ gửi yêu cầu tới điện thoại!!!
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }

                    //Nếu đã có allow Camera và Thư viện thì else
                    else {
                        pickFromCamera();
                    }
                }

                //Vào thư viện ảnh
                else if(i == 1){

                    //Check xem điện thoại mình có đc allow thư viên không
                    //Nếu chưa có thì sẽ gửi yêu cầu tới điện thoại!!!
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }

                    //Nếu đã có allow Thư viên thì else
                    else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }




    //Camera và thư viên
    //Kiểm tra camera có được bật hay không
    private boolean checkCameraPermission() {
        //Thư viện ảnh
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        //Camera
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    //Gửi yêu cầu đến điện thoại quyền sử dụng Camera và  thư viện
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST);
    }



    //Thư viên ảnh riêng
    //Kiểm tra thư viên ảnh có được bật hay không
    private boolean checkStoragePermission() {
        //Thư viện ảnh
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    //Gửi yêu cầu đến điện thoại quyền sử dụng thư viện ảnh
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST);
    }




    //Vào camera
    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image description");
        //image uri
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //mở camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA);
    }


    //Vào thư viên ảnh
    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        //Only image
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY);
    }




    //Trả kết quả của Camera và Thư Viên (Allowed / Denied)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST:{
                //Nếu CAMERA và THƯ VIỆN đã được allow thì true / Denied thì false
                if (grantResults.length>0){
                    //camera
                    boolean camera_accepted =grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    //thư viện ảnh
                    boolean storage_accepted =grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    //Cả 2 đều đc allow
                    if (camera_accepted && storage_accepted){
                        pickFromGallery();
                    }

                    //false
                    else{
                        Toast.makeText(this, "Yêu cầu thư viên ảnh và Camera", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST:{
                //Nếu THƯ VIỆN đã được allow thì true / Denied thì false
                if(grantResults.length>0){
                    //thư viện ảnh
                    boolean storage_accepted =grantResults[0]==PackageManager.PERMISSION_GRANTED;

                    //thư viện được allow
                    if(storage_accepted){
                        pickFromGallery();
                    }

                    //false
                    else {
                        Toast.makeText(this, "Yêu cầu thư viên ảnh", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }




    //Hình ảnh được thay đổi do Camera hoặc Thư viện ảnh sẽ trả kết quả ở đây
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Trả kết quá Image từ Camera và Gallery ở đây
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY){
                //Được trả từ Thư viên ảnh

                //Crop Hình ảnh
                //Kéo hình ảnh vị trí mình muốn
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA){
                //Được trả từ Camera

                //Crop Hình ảnh
                //Kéo hình ảnh vị trí mình muốn
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }

            //Crop Hình ảnh trả kết quả
            else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if(resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;

                    //Set hình ảnh vừa crop cho Image View
                    mimgDangBai.setImageURI(resultUri);
                }
                //error
                else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this,"" + error, Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    //private byte[] ImageViewToByte(ImageView mimgDangBai) {
    //Bitmap bitmap=((BitmapDrawable)mimgDangBai.getDrawable()).getBitmap();
    //ByteArrayOutputStream stream = new ByteArrayOutputStream();
    //bitmap.compress(Bitmap.CompressFormat.PNG,80,stream);
    //byte[]bytes = stream.toByteArray();
    //return bytes;
    //}
}