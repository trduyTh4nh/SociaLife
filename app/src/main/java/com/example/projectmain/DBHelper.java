package com.example.projectmain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
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
        //notification chưa nên ghi nào
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
    }

    //Get ID của user để truyển qua cho Account
    public int getIduser(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //Do name không thể trùng nên ta tìm theo name
        //Tìm theo name để xuất id
        Cursor cursor = MyDB.rawQuery("Select * from user where name = ?", new String[]{name});
        //getCount để lấy id
        return cursor.getCount();
    }

    //Insert thông tin của User
    public Boolean insertUser(String name) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        long result = MyDB.insert("user" , null, contentValues);
        if(result==-1) return false;
        else
        return true;
    }

    //Insert thông tin của Account
    public Boolean insertData(int iduser,String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("iduser", iduser);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDB.insert("account" , null, contentValues);
        if(result==-1) return false;
        else
            return true;
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
    public Boolean CheckEmailPassword(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from account where email = ? and password = ?", new String[] {email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
