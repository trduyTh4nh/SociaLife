package com.example.projectmain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
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
import android.provider.MediaStore;
import android.Manifest;
import android.widget.Toast;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Fragment.HomeFragment;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.Refactoring.Builder.ContentValueBuilder;
import com.example.projectmain.Refactoring.Builder.Director;
import com.example.projectmain.Refactoring.Builder.TextAndImageBuilder;
import com.example.projectmain.Refactoring.Observer.AccountFl;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

public class    AddActivity extends AppCompatActivity {

    EditText medtNoidung;
    TextView mtvName, tvFullName;
    ImageView ivPfp;
    Button mbtnDangBai;
    ImageView mimgDangBai;
    ImageButton btnExit;
    DB db;
    User user;
    SharedPreferences sharedPreferences;

    Uri imageUri;


    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    public static final int IMAGE_PICK_GALLERY = 102;
    public static final int IMAGE_PICK_CAMERA = 103;

    private static String[] cameraPermission;
    private static String[] storagePermission;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE_LINK = "linkImage";

    AccountFl accountFl = new AccountFl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // init
        initView();
        db = new DB(this);
        user = new User();
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        user = GlobalUser.getInstance(this).getUser();
        String strImageAvatar = db.getImagefor(user.getId());
        String name = user.getName();
        if (strImageAvatar != null) {
            ivPfp.setImageURI(Uri.parse(strImageAvatar));
        } else
            ivPfp.setImageResource(R.drawable.def);

        mtvName.setText(name);
        tvFullName.setText(name);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mbtnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = medtNoidung.getText().toString();
                if (content.equals("")) {
                    Toast.makeText(AddActivity.this, "Hãy nhập nội dung bài viết", Toast.LENGTH_SHORT).show();
                } else {

                    int iduser = db.getIduser(name);
                    SQLiteDatabase myDB = db.getWritableDatabase();


//                    ContentValues contentValues = new ContentValues();
//
//                    contentValues.put("iduser", iduser);
//                    contentValues.put("content", content);
//                    contentValues.put("image",String.valueOf(imageUri));
//                    contentValues.put("isshare", 0);
//
//                    Calendar c = Calendar.getInstance();
//                    long t = c.getTimeInMillis();
//                    contentValues.put("datetime", String.valueOf(t));
//                    contentValues.put("image", String.valueOf(imageUri));


                    Log.d("ID USER IN Acitivty: ", String.valueOf(iduser));
                    // BUILDER DESIGN PATTERN
                    TextAndImageBuilder post = new TextAndImageBuilder();
                    Director director = new Director(post, iduser);
                    ContentValues contentValues = director.buildImageAndContentPost(imageUri, content, 0);

                    Log.d("ContentValues", "iduser: " + contentValues.get("iduser"));
                    Log.d("ContentValues", "content: " + contentValues.get("content"));
                    Log.d("ContentValues", "image: " + contentValues.get("image"));
                    Log.d("ContentValues", "isshare: " + contentValues.get("isshare"));
                    Log.d("ContentValues", "datetime: " + contentValues.get("datetime"));

                    int idPost = db.getIDPostOf(iduser);

                    int idUserFollower = 0;
                    int idshare = 0;
                    int idlike = 0;
                    int idComment = 0;

                    Date currentTime = Calendar.getInstance().getTime();
                    Log.d("Time: ", String.valueOf(currentTime));
                 //   db.insertNotify(user.getId(), user.getName() + " đã đăng 1 bài viết", String.valueOf(currentTime), idPost, idlike, idComment, idshare, idUserFollower);
                    Log.d("Content Value of post: ", String.valueOf(contentValues));
                    long result = myDB.insert("post", null, contentValues);

                    if (result > 0) {
                        Toast.makeText(AddActivity.this, "Đăng bài thành công", Toast.LENGTH_SHORT).show();

                        Log.d("ID BAI POST DA DANG: ", String.valueOf(idPost));
                     //   accountFl.setUser(user);
                        accountFl.update();
//                            accountFl.setPost(p);
//                            accountFl.update();
//                        Log.d("POST DA DANG: ", p.getContent());
                        finish();
                    } else {
                        Toast.makeText(AddActivity.this, "Đăng bài thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mimgDangBai.setOnClickListener(new View.OnClickListener() {
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
                    mimgDangBai.setImageURI(resultUri);
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

    public void initView() {
        mtvName = findViewById(R.id.vbn);
        medtNoidung = findViewById(R.id.qwe);
        mbtnDangBai = findViewById(R.id.zxc);
        mimgDangBai = findViewById(R.id.asd);
        tvFullName = findViewById(R.id.tvName);
        ivPfp = findViewById(R.id.ivPfp);
        btnExit = findViewById(R.id.btn_exit);
    }
}