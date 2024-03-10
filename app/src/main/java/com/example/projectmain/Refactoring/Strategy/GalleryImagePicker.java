package com.example.projectmain.Refactoring.Strategy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class GalleryImagePicker implements IimagePicker {

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    public static final int IMAGE_PICK_GALLERY = 102;
    public static final int IMAGE_PICK_CAMERA = 103;

    private static String[] cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static String[] storagePermission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    private Context context;

    private Uri imageUri;
    private Activity ac;

    public GalleryImagePicker(Context context, Activity ac){
        this.context = context;
        this.ac = ac;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public GalleryImagePicker(Activity ac){
        this.ac = ac;
    }

    @Override
    public void pickImage(Activity activity) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        activity.startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY);
    }

    private boolean CheckCamneraPermission() {
        //boolean result_storage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
    }


    private void pickFromGallery(Activity activity) {

    }




}
