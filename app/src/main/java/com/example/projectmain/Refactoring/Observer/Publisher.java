package com.example.projectmain.Refactoring.Observer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Publisher  {


    private DB db;

    private Context context;

    public Publisher(Context context) {
        this.context = context;
    }

    public void thongBao(){
        db = new DB(context);

        User userMain = GlobalUser.getInstance(context).getUser();

        Log.d("TAO NE: ", userMain.getName());

        Date currentTime = Calendar.getInstance().getTime();

        List<Integer> followerIds = getAllMyFollower(userMain.getId());
        for (int i = 0; i < followerIds.size(); i++){
            db.insertNotify(userMain.getId(), userMain.getName() + " đã đăng 1 bài viết", String.valueOf(currentTime), 0, 0, 0, 0, followerIds.get(i));
        }
    }


    public List<Integer> getAllMyFollower(int idUser){
        db = new DB(context);
        return db.getFollowerIds(idUser);
    }

}
