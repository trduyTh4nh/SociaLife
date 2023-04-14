package com.example.projectmain.Database;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.projectmain.Fragment.SreachFragment;
import com.example.projectmain.MainActivity;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


public class DB extends SQLiteOpenHelper {
    public DB(Context context) {
        super(context, "dbSocialNetwork.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        //account
        myDB.execSQL("create Table account(" +
                "id Integer PRIMARY KEY NOT NULL UNIQUE," +
                "iduser Integer REFERENCES user(id) NOT NULL," +
                "email Text," +
                "password Text)");
        //user
        myDB.execSQL("create Table user(" +
                "id Integer PRIMARY KEY NOT NULL UNIQUE," +
                "name Text," +
                "image Blob," +
                "post_count Integer NOT NULL DEFAULT (0)," +
                "follower_count Integer NOT NULL DEFAULT (0)," +
                "following_count Integer NOT NULL DEFAULT (0)," +
                "description  TEXT)");
        //post
        myDB.execSQL("create Table post(" +
                "id Integer PRIMARY KEY NOT NULL UNIQUE," +
                "iduser Integer REFERENCES user(id) NOT NULL," +
                "content Text," +
                "image Blob," +
                "like_count Integer NOT NULL DEFAULT (0)," +
                "comment_count Integer NOT NULL DEFAULT (0)," +
                "share_count Integer NOT NULL DEFAULT (0)," +
                "datetime Datetime)");

        //likes
        myDB.execSQL("create Table likes(" +
                "id Integer PRIMARY KEY NOT NULL UNIQUE," +
                "iduser Integer REFERENCES user(id) NOT NULL," +
                "idpost Integer REFERENCES post(id) NOT NULL," +
                "datetime Datetime)");
        //comment
        myDB.execSQL("create Table comment(" +
                "id Integer PRIMARY KEY NOT NULL UNIQUE," +
                "iduser Integer REFERENCES user(id) NOT NULL," +
                "idpost Integer REFERENCES post(id) NOT NULL," +
                "content Text," +
                "datetime Datetime)");
        //share
        myDB.execSQL("create Table share(" +
                "id Integer PRIMARY KEY NOT NULL UNIQUE," +
                "iduser Integer REFERENCES user(id) NOT NULL," +
                "idpost Integer REFERENCES post(id) NOT NULL," +
                "datetime Datetime)");
        //follower
        myDB.execSQL("create Table follower(" +
                "id Integer PRIMARY KEY NOT NULL UNIQUE," +
                "iduser Integer REFERENCES user(id) NOT NULL," +
                "idfollowing Integer REFERENCES user(id) NOT NULL)");
        //notification
        myDB.execSQL("create Table notification (" +
                "id Integer PRIMARY KEY NOT NULL UNIQUE, " +
                "iduser Interger REFERENCES user(id) NOT NULL, " +
                "content Text, " +
                "datetime Datetime, " +
                "idpost Integer REFERENCES post(id) NOT NULL," +
                " idlike Integer REFERENCES likes(id) NOT NULL," +
                " idcomment Integer REFERENCES comment(id) NOT NULL, " +
                "idshare Integer REFERENCES share(id) NOT NULL, " +
                "idfollower Integer REFERENCES follower(id) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop Table if exists account");
        myDB.execSQL("drop Table if exists user");
        myDB.execSQL("drop Table if exists post");
        myDB.execSQL("drop Table if exists likes");
        myDB.execSQL("drop Table if exists comment");
        myDB.execSQL("drop Table if exists share");
        myDB.execSQL("drop Table if exists follower");
        myDB.execSQL("drop Table if exists notification");
    }

    //Get ID của user để truyển qua cho Account
    public int getIduser(String name) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        //Do name không thể trùng nên ta tìm theo name
        //Tìm theo name để xuất id
        Cursor cursor = MyDB.rawQuery("Select * from user where name = ?", new String[]{name});
        //getCount để lấy id
        int id = -1;
        if (cursor.moveToFirst())
            id = cursor.getInt(0);

        cursor.close();
        MyDB.close();

        return id;
    }


    //Insert thông tin của User

    public Boolean insertUser(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        long result = MyDB.insert("user", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    //Insert thông tin của Account

    public Boolean insertData(int iduser, String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("iduser", iduser);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDB.insert("account", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    // comment bài post
    public void insertComment(int idUser, int idPost, String content) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        //Cursor cursor = db.rawQuery("SELECT u.* FROM user u JOIN post p on u.id = p.iduser", null);
        contentValues.put("idUser", idUser);
        contentValues.put("idPost", idPost);
        db.insert("comment", null, contentValues);

    }
    // lấy tên người dùng ra theo id
    public String getName(int IdUser){
        SQLiteDatabase db = this.getWritableDatabase();


       Cursor cursor = db.rawQuery("SELECT u.* FROM user u  WHERE u.id = ?", new String[]{String.valueOf(IdUser)});
     //   Cursor cursor = db.query("user", null, null, null, null, null, null);
        String name = "";
        while (cursor.moveToNext()){
             name = cursor.getString(1);
        }
        return name;

    }
    public String getImgAvata(int IdUser){
        SQLiteDatabase db = this.getWritableDatabase();
        //  Cursor cursor = db.rawQuery("SELECT u.* FROM user u  WHERE u.id = ?", new String[]{String.valueOf(IdUser)});
          Cursor cursor = db.query("user", null, "id = ?", new String[]{String.valueOf(IdUser)}, null, null, null);
        String link = "";
        while (cursor.moveToNext()){
            link = cursor.getString(2);
        }
        return String.valueOf(link);
    }


    public void UpdateDataEditInfo(User user, String name, String des, String Image) {

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", des);
        contentValues.put("image", Image);

        myDB.update("user", contentValues, "id = ?", new String[]{String.valueOf(user.getId())});
        myDB.close();
    }


    public void CheckPassWord(String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT password FROM account ac JOIN user u on u.id = ac.iduser WHERE ac.password = ?", new String[]{password});

    }

    //Kiểm tra Email Account trong SQLite?
    public Boolean CheckEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from account where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //Kiểm tra Name User trong SQLite
    public Boolean CheckName(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where name = ?", new String[]{name});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //Kiểm tra Email , Password trong SQLite?
    public Boolean CheckEmailPassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from account where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    // post

    public boolean insertPost(int iduser, String content) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("iduser", iduser);
        contentValues.put("content", content);

        long result = MyDB.insert("post", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    //
    @SuppressLint("Range")
    public User getUser(String email) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT u.* FROM user u JOIN account ac on u.id = ac.iduser WHERE ac.email = ?", new String[]{email});
//        Cursor cursor = myDB.rawQuery("SELECT * FROM user INNER JOIN account on account.id = user.userid WHERE account.email = ?", new String[]{email});
        User user = new User();
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
            user.setPost_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex("post_count"))));
            user.setFollower_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex("follower_count"))));
            user.setFollowing_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex("following_count"))));
            user.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        }
        cursor.close();
        myDB.close();
        return user;
    }


    public List<String> getListName() {
        String[] column = {"name"};

        List<String> listName = new ArrayList<String>();

        SQLiteDatabase mydb = this.getReadableDatabase();

        Cursor cursor = mydb.query("user", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(1);

            listName.add(name);
        }
        return listName;
    }

    public List<String> getListNameID() {
        String[] column = {"name"};

        List<String> listName = new ArrayList<String>();

        SQLiteDatabase mydb = this.getReadableDatabase();

        Cursor cursor = mydb.rawQuery("SELECT u.* FROM user u JOIN post p on u.id = p.iduser", null);

        while (cursor.moveToNext()) {

            String name = cursor.getString(1);

            listName.add(name);
        }
        return listName;
    }

}
