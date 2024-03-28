package com.example.projectmain;

import static com.example.projectmain.AddActivity.CAMERA_REQUEST;
import static com.example.projectmain.AddActivity.IMAGE_PICK_CAMERA;
import static com.example.projectmain.AddActivity.IMAGE_PICK_GALLERY;
import static com.example.projectmain.AddActivity.STORAGE_REQUEST;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Fragment.UserFragment;
import com.example.projectmain.Model.User;
import com.example.projectmain.Refactoring.Proxy.UserManager;
import com.example.projectmain.Refactoring.Proxy.UserProxy;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class    EditInfoActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtUserName, edtPassword, edtConfirmPass, edtStory;
    ImageView imgCurrent;
    Button btnSave;
    ImageButton btnExit;
    Button btnChangeImage;
    DB db;

    SharedPreferences sharedPreferences;
    SQLiteDatabase mydb;


    User user;

    Uri imageUri;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    private static final String KEY_PASSWORD = "password";

    private static final String KEY_DESCRIPTION = "description";

    private static final String KEY_IMAGE_LINK = "linkImage";
    private static final String KEY_NAME = "name";


    private String[] cameraPermission;
    private String[] storagePermission;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initView();


        cameraPermission = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        db = new DB(this);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        user = GlobalUser.getInstance(this).getUser();
        edtUserName.setText(user.getName());
        if(db.getImagefor(user.getId()) == null){
            imgCurrent.setImageResource(R.drawable.def);
        } else {
            imageUri = Uri.parse(db.getImagefor(user.getId()));
            imgCurrent.setImageURI(imageUri);
        }
        edtStory.setText(user.getDescription());
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



//        String newName = edtName.getText().toString();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProxy proxy = new UserProxy(new UserManager(getApplicationContext(), user), getApplicationContext());
                if(proxy.getUser() != null){
                    user.setName(edtUserName.getText().toString());
                    user.setDescription(edtStory.getText().toString());
                    user.setImage(imageUri.toString());
                    proxy.postEditedInfo(user, edtPassword.getText().toString(), edtConfirmPass.getText().toString());
                } else {
                    Toast.makeText(EditInfoActivity.this, "Không có quyền truy cập!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePickDialog();
            }
        });


    }

    public void ImagePickDialog() {
        String[] option = {"Camera", "Thư viện"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Chọn ảnh từ ");

        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    if (!CheckCamneraPermission()) {
                        requestCameraPermisson();
                    } else {
                        pickFormCamera();
                    }
                } else if (i == 1) {
                    if (!CheckCamneraPermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });

        builder.create().show();
    }


    private boolean CheckCamneraPermission() {
        boolean result_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        boolean result_camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return result_camera && result_storage;
    }

    private void requestCameraPermisson() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST);
    }

    private boolean CheckStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST);
    }

    private void pickFormCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description");


        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA);

    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);

        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    boolean storage_accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (camera_accepted) {
                        pickFormCamera();
                    } else {
                        Toast.makeText(this, "Yêu cầu thư viện ảnh và camera", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST:
                if (grantResults.length > 0) {
                    boolean storage_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S || storage_accepted) {
                        pickFromGallery();
                    } else
                        Toast.makeText(this, "Yêu cầu thư viện ảnh", Toast.LENGTH_SHORT).show();
                }
        }
    }


    //Hình ảnh được thay đổi do Camera hoặc Thư viện ảnh sẽ trả kết quả ở đây
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Trả kết quá Image từ Camera và Gallery ở đây
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY) {
                //Được trả từ Thư viên ảnh

                //Crop Hình ảnh
                //Kéo hình ảnh vị trí mình muốn
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            } else if (requestCode == IMAGE_PICK_CAMERA) {
                //Được trả từ Camera

                //Crop Hình ảnh
                //Kéo hình ảnh vị trí mình muốn
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }

            //Crop Hình ảnh trả kết quả
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;

                    //Set hình ảnh vừa crop cho Image View
                    imgCurrent.setImageURI(resultUri);
                }
                //error
                else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        btnExit = findViewById(R.id.btnExit);
        imgCurrent = findViewById(R.id.avart_current);
        btnSave = findViewById(R.id.btnSave);
        btnChangeImage = findViewById(R.id.btnEdit_image);
        edtUserName = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPass = findViewById(R.id.edt_confirmPass);
        edtStory = findViewById(R.id.edt_story);
    }
}