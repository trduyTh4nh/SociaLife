package com.example.projectmain.Refactoring.Proxy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.Toast;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;

import java.util.ArrayList;

public class UserManager implements IUserManager {
    Context c;
    User user;

    public UserManager(Context context, User target) {
        this.c = context;
        this.user = GlobalUser.getInstance(context).getUser();
    }

    @SuppressLint("Range")
    @Override
    public ArrayList<Post> getOwnPosts() {
        ArrayList<Post> postList = new ArrayList<>();
        DB database = new DB(c);
        Cursor postCur = database.getPostsFromUser(user.getId());
        while (postCur.moveToNext()){
            if(postCur.getInt(postCur.getColumnIndex("isshare")) == 1){
                continue;
            }
            Post temp = new Post();
            temp.setId(postCur.getInt(postCur.getColumnIndex("id")));
            temp.setIduser(user.getId());
            temp.setImgPost(postCur.getString(3));
            temp.setContent(postCur.getString(postCur.getColumnIndex("content")));
            temp.setNumber_like(postCur.getString(postCur.getColumnIndex("like_count")));
            temp.setTime(postCur.getString(postCur.getColumnIndex("datetime")));
            postList.add(temp);
        }
        return postList;
    }

    @Override
    public void postEditedInfo(User newInfo, String password, String confirmPass) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(GlobalUser.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String pass = sharedPreferences.getString("password", null);
        String userName = newInfo.getName();
        String story = newInfo.getDescription();
        DB db = new DB(c);
        ContentValues contentValues = new ContentValues();
        contentValues.put("imageEdit", String.valueOf(newInfo.getImage()));
        String linkImage = String.valueOf(newInfo.getImage());
        SharedPreferences.Editor saveImage = sharedPreferences.edit();
        saveImage.putString("linkImage", linkImage);
        if (password.equals(pass)) {
            if (!password.equals("") && !confirmPass.equals("") && password.equals(confirmPass)) {
                db.UpdateDataEditInfo(user, userName, story, linkImage);
                Toast.makeText(c, "Thành công", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(c, "Nhập mật khẩu để xác nhận!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(c, "Sai mật khẩu.", Toast.LENGTH_SHORT).show();
        saveImage.apply();
    }

    @Override
    public User getUser() {
        return user;
    }
}
