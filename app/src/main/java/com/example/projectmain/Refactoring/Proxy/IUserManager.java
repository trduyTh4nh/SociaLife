package com.example.projectmain.Refactoring.Proxy;

import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;

import java.util.ArrayList;

public interface IUserManager {
    ArrayList<Post> getOwnPosts();

    void postEditedInfo(User newInfo, String password, String confirmPass);

    User getUser();

}
