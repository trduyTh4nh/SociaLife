package com.example.projectmain.Refactoring.Observer;

import android.content.Context;
import android.util.Log;

import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;

public class AccountFl implements IObserver {

    Publisher publisher;
    User user;

    Context context;
    public AccountFl(Context context) {
        this.context = context;
    }


    public AccountFl(){

    }

    @Override
    public void update() {
        publisher = new Publisher(context);
       // Log.d("POST DA DANG: ", post.getContent());
        publisher.thongBao();
    }
}
