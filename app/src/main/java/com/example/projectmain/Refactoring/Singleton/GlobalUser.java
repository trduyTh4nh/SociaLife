package com.example.projectmain.Refactoring.Singleton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.util.Log;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Account;
import com.example.projectmain.Model.User;

import java.sql.SQLClientInfoException;
import java.util.List;

public final class GlobalUser {
    public static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    private final String KEY_PASSWORD = "password";

    private final String KEY_DESCRIPTION = "description";

    private final String KEY_IMAGE_LINK = "linkImage";
    private final String KEY_NAME = "name";


    private final User user;
    private final Account account;
    private static GlobalUser INSTANCE;

    private GlobalUser(User user, Account account) {
        this.user = user;
        this.account = account;
    }

    public static GlobalUser getInstance(Context c){
        DB db = new DB(c);
        SharedPreferences sharedPreferences = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        Account tmpAccount = new Account();
        tmpAccount.setEmail(email);
        INSTANCE = new GlobalUser(db.getUser(email), tmpAccount);
        return INSTANCE;
    }

    public User getUser() {
        return user;
    }
    public Account getAccount(){
        return account;
    }
}
