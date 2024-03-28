package com.example.projectmain.Refactoring.Proxy;

import android.content.Context;

import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;

import java.util.ArrayList;

public class UserProxy implements IUserManager {
    Context context;
    private UserManager realUserManager;
    public UserProxy(UserManager editor, Context context){
        realUserManager = editor;
        this.context = context;
    }
    public boolean checkAccess(){
        User user = GlobalUser.getInstance(context).getUser();
        return user.getId() == realUserManager.getUser().getId();
    }

    @Override
    public ArrayList<Post> getOwnPosts() {
        if(checkAccess()){
            return realUserManager.getOwnPosts();
        }
        return new ArrayList<>();
    }

    @Override
    public void postEditedInfo(User newInfo, String password, String confirmPass) {
        if(checkAccess()){
            realUserManager.postEditedInfo(newInfo, password, confirmPass);
        }
    }

    @Override
    public User getUser() {
        if(checkAccess()){
            return realUserManager.getUser();
        }
        return null;
    }
}
