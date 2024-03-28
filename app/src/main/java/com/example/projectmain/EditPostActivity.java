package com.example.projectmain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Refactoring.State.EditedPostState;
import com.example.projectmain.Refactoring.State.PostContext;
import com.example.projectmain.Refactoring.Strategy.CameraImagePicker;
import com.example.projectmain.Refactoring.Strategy.GalleryImagePicker;
import com.example.projectmain.Refactoring.Strategy.IimagePicker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class EditPostActivity extends AppCompatActivity {
    int id;
    DB db;
    Post p;
    EditText edtContent;
    TextView tvNoImg;
    ImageView ivPost;
    ImageButton btnSave, btnDelete, btnExit;
    Button resotreImage, getNewImage, getImageGallery;
    ConstraintLayout ImageWrapper;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";


    Uri imageUri;

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    public static final int IMAGE_PICK_GALLERY = 102;
    public static final int IMAGE_PICK_CAMERA = 103;

    SharedPreferences share;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        resotreImage = findViewById(R.id.btnRedo);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_deleteimg);
        share = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        ImageWrapper = findViewById(R.id.ImageWrapper);
        tvNoImg = findViewById(R.id.noImage);
        btnExit = findViewById(R.id.btn_exit);
        getNewImage = findViewById(R.id.btnNewImage);
        getImageGallery = findViewById(R.id.btnNewImageLib);

        String name = share.getString(KEY_NAME, null);
        db = new DB(this);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        id = b.getInt("idPost");
        Uri tempUri = null;
        String tempImage = null;
        p = db.getPostFromID(id, name);

        if(p.getImgPost().equals("null")){
            ImageWrapper.setVisibility(View.GONE);
            tvNoImg.setVisibility(View.VISIBLE);
        } else {
            tempUri = Uri.parse(p.getImgPost());
            tempImage = p.getImgPost();
        }

        edtContent = findViewById(R.id.edtEdit);
        ivPost = findViewById(R.id.img_post);

        edtContent.setText(p.getContent());
        ivPost.setImageURI(Uri.parse(p.getImgPost()));
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtContent.getText().toString().equals("")){
                    Toast.makeText(EditPostActivity.this, "Nội dung bị trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                p.setContent(edtContent.getText().toString());
                long l = db.UpdatePost(p);

                // user State design pattern

                EditedPostState editedPostState = new EditedPostState(EditPostActivity.this);
                PostContext postContext = new PostContext(editedPostState);
                postContext.editState(p.getId());

                if(l == 0){
                    Toast.makeText(EditPostActivity.this, "lỗi!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setImgPost("null");
                ImageWrapper.setVisibility(View.GONE);
                resotreImage.setVisibility(View.VISIBLE);
                getNewImage.setVisibility(View.VISIBLE);
                getImageGallery.setVisibility(View.VISIBLE);
            }
        });

        // 2 nút sử dụng strategy
        getNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IimagePicker picker = new CameraImagePicker(EditPostActivity.this,EditPostActivity.this);
                picker.pickImage(EditPostActivity.this);
            }
        });
        getImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IimagePicker picker = new GalleryImagePicker(EditPostActivity.this, EditPostActivity.this);
                picker.pickImage(EditPostActivity.this);
            }
        });


        Uri finalTempUri = tempUri;
        String finalTempImage = tempImage;
        resotreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivPost.setImageURI(finalTempUri);
                p.setImgPost(finalTempImage);
                ImageWrapper.setVisibility(View.VISIBLE);
                resotreImage.setVisibility(View.GONE);
                getNewImage.setVisibility(View.GONE);
                getImageGallery.setVisibility(View.GONE);
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case CAMERA_REQUEST:
//                if (grantResults.length > 0) {
//                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//
//                    boolean storage_accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (camera_accepted) {
//                    //    pickFormCamera();
//                    } else {
//                        Toast.makeText(this, "Yêu cầu thư viện ảnh và camera", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//            case STORAGE_REQUEST:
//                if (grantResults.length > 0) {
//                    boolean storage_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//
//                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S || storage_accepted) {
//                    //    pickFromGallery();
//                    } else
//                        Toast.makeText(this, "Yêu cầu thư viện ảnh", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }

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
                    ivPost.setImageURI(resultUri);
                    p.setImgPost(String.valueOf(resultUri));
                    ImageWrapper.setVisibility(View.VISIBLE);
                    resotreImage.setVisibility(View.GONE);
                    getNewImage.setVisibility(View.GONE);
                    getImageGallery.setVisibility(View.GONE);


                    Log.d("Current image uri: ", String.valueOf(resultUri));
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


}