package com.example.projectmain.Database;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbSocialNetwork";

    private static final String TABLE_ACCOUNT = "account";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final String USERNAME = "username";

    private static final int VERSION = 1;

    private Context context;
    private String SQLQuery = "CREATE TABLE " + TABLE_ACCOUNT + " (" +
            ID + " INTEGER PRIMARY KEY NOT NULL UNIQUE, " +
            EMAIL + " TEXT, " +
            PASSWORD + " TEXT, " +
            USERNAME + "TEXT)";


    public DB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);

        // Create tables again
        onCreate(db);

    }

    public Boolean insertData(String email, String username, String password) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();

            values.put(EMAIL, email);
            values.put(USERNAME, username);
            values.put(PASSWORD, password);

            long result = db.insert(TABLE_ACCOUNT, null, values);

            db.update(TABLE_ACCOUNT, values, "rowid=?", new String[]{String.valueOf(result)});

            return result > 0;

        } catch (Exception ex) {
            Log.e(TAG, "Error guardando registro", ex);

            return false;
        }
    }

    // signup
    public Boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT + " WHERE email = ?", new String[]{email});

        if (cursor.getCount() > 0) {
            return true;
        } else
            return false;
    }

    // login
    public Boolean CheckEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT FROM" + TABLE_ACCOUNT + " WHERE email = ? AND password = ?", new String[]{email, password});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

}
