package com.example.projectmain.Refactoring.Strategy;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CameraImagePicker implements IimagePicker {

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    public static final int IMAGE_PICK_CAMERA = 103;

    private static String[] cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    private Context context;

    private Uri imageUri;
    private Activity ac;
    public CameraImagePicker(Context context, Activity ac){
        this.context = (Context) context;
        this.ac = ac;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public void pickImage(Activity activity) {
        if (!CheckCamneraPermission()){
            requestCameraPermisson();
        }
        else {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "Image title");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description");
            imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            activity.startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA);
        }
    }

    private boolean CheckCamneraPermission() {
        boolean result_storage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestCameraPermisson() {
        ActivityCompat.requestPermissions((Activity) context, cameraPermission, CAMERA_REQUEST);
    }

//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case CAMERA_REQUEST:
//                if (grantResults.length > 0) {
//                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//
//                    if (cameraAccepted && storageAccepted) {
//                        pickFromCamera(ac);
//                    } else {
//                        Toast.makeText(context, "Yêu cầu quyền camera và thư viện ảnh", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//        }
//    }

}

