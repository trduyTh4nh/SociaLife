package com.example.projectmain.Refactoring.Proxy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

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
    public void postEditedInfo(User newInfo) {
        //TODO: Sau khi thanh xong Builder
    }

    @Override
    public User getUser() {
        return user;
    }
}
