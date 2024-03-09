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
import com.example.projectmain.Model.TimeHelper;
import com.example.projectmain.Model.User;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DB extends SQLiteOpenHelper {
    public DB(Context context) {
        super(context, "dbSocialNetwork.db", null, 3);
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
                "isshare Integer," +
                "like_count Integer NOT NULL DEFAULT (0)," +
                "comment_count Integer NOT NULL DEFAULT (0)," +
                "share_count Integer NOT NULL DEFAULT (0)," +
                "   datetime Datetime)");



//        myDB.execSQL("ALTER TABLE post add isshare Integer NOT NULL DEFAULT (0)");
       // myDB.execSQL("ALTER TABLE post drop column isshare");
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
                "frompost Integer REFERENCES post(id) NOT NULL,"+
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
                "idlike Integer REFERENCES likes(id) NOT NULL," +
                " idcomment Integer REFERENCES comment(id) NOT NULL, " +
                "idshare Integer REFERENCES share(id) NOT NULL, " +
                "idfollower Integer REFERENCES follower(id) NOT NULL)");
    }

    public void saveShare(int idUser, int idCurPost, String curTime){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        insertPostShare(idUser);
        int frompost = getJustAddedPost();
        contentValues.put("iduser", idUser);
        contentValues.put("idpost", idCurPost);
        contentValues.put("datetime", curTime);
        contentValues.put("frompost", frompost);
        database.insert("share", null, contentValues);
    }




    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {

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

//        cursor.close();
//        MyDB.close();

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
    public String getName(int IdUser) {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT u.* FROM user u  WHERE u.id = ?", new String[]{String.valueOf(IdUser)});
        //   Cursor cursor = db.query("user", null, null, null, null, null, null);
        String name = "";
        while (cursor.moveToNext()) {
            name = cursor.getString(1);
        }
        return name;

    }

    public String getImgAvata(int IdUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        //  Cursor cursor = db.rawQuery("SELECT u.* FROM user u  WHERE u.id = ?", new String[]{String.valueOf(IdUser)});
        Cursor cursor = db.query("user", null, "id = ?", new String[]{String.valueOf(IdUser)}, null, null, null);
        String link = "";
        while (cursor.moveToNext()) {
            link = cursor.getString(2);
        }
        return String.valueOf(link);
    }


    // cập nhật dữ liệu người dùng
    public void UpdateDataEditInfo(User user, String name, String des, String Image) {

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", des);
        contentValues.put("image", Image);

        myDB.update("user", contentValues, "id = ?", new String[]{String.valueOf(user.getId())});
        //  myDB.close();
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

    // kiểm tra người đã follow
    public Boolean CheckNameinFollower(int IDuserFollowing) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from follower where idfollowing = ?", new String[]{String.valueOf(IDuserFollowing)});
        return cursor.getCount() > 0;
    }

    //Kiểm tra Email , Password trong SQLite?
    public Boolean CheckEmailPassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor cursor = MyDB.rawQuery("Select * from account where email = ? and password = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

    // insert post
    public int getJustAddedPost(){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor c = MyDB.query("post", null,"isshare =?", new String[]{"1"}, "id", null, "id desc");
        c.moveToFirst();
        int id = c.getInt(0);
        return id;
    }
    public void insertPostShare(int iduser) {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("iduser", iduser);
        contentValues.put("datetime", TimeHelper.getCurrentTime());
        contentValues.put("isshare", 1);
        long result = MyDB.insert("post", null, contentValues);
    }
    public void RemoveSharedPost(int idpost){
        removePost(idpost);
        SQLiteDatabase db = getWritableDatabase();
        db.delete("share", "frompost =?", new String[]{String.valueOf(idpost)});
    }
    // remove post

    public void removePost(int idPost) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("likes", "idpost = ?", new String[]{String.valueOf(idPost)});
        database.delete("share", "idpost = ?", new String[]{String.valueOf(idPost)});
        database.delete("post", "id = ?", new String[]{String.valueOf(idPost)});
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
            user.setImage(cursor.getString(cursor.getColumnIndex("image")));
            user.setPost_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex("post_count"))));
            user.setFollower_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex("follower_count"))));
            user.setFollowing_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex("following_count"))));
            user.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        }
//        cursor.close();
//        myDB.close();
        return user;
    }

    @SuppressLint("Range")
    public User getUser(int id) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT u.* FROM user u JOIN account ac on u.id = ac.iduser WHERE u.id = ?", new String[]{String.valueOf(id)});
//        Cursor cursor = myDB.rawQuery("SELECT * FROM user INNER JOIN account on account.id = user.userid WHERE account.email = ?", new String[]{email});
        User user = new User();
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setImage(cursor.getString(cursor.getColumnIndex("image")));
            user.setPost_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex("post_count"))));
            user.setFollower_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex("follower_count"))));
            user.setFollowing_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex("following_count"))));
            user.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        }
//        cursor.close();
//        myDB.close();
        return user;
    }

    @SuppressLint("Range")
    public Cursor getUserFromSearch(String keyword) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        //        Cursor cursor = myDB.rawQuery("SELECT * FROM user INNER JOIN account on account.id = user.userid WHERE account.email = ?", new String[]{email});
//        cursor.close();
//        myDB.close();
        return myDB.rawQuery("SELECT u.* FROM user u JOIN account ac on u.id = ac.iduser WHERE u.name LIKE '%" + keyword + "%'", null);
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

    // Follow

    public boolean insertDataFollow(int idUser, int idUserFollow) {
        String[] cot = {"iduser", "idfollowing"};
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("iduser", idUser);
        contentValues.put("idfollowing", idUserFollow);

        long insertData = db.insert("follower", null, contentValues);

        return insertData != -1;
    }

    // unfollower
    public void UnFollower(int idFollowing) {
        SQLiteDatabase database = this.getWritableDatabase();
        if (idFollowing >= 0)
            database.delete("follower", "idfollowing = ?", new String[]{String.valueOf(idFollowing)});
    }


    public Cursor getDataFromID(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] Image = {"image"};
        return db.query("user", Image, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public String getImagefor(int idUser) {
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.query("user", null, "id=?", new String[]{String.valueOf(idUser)}, null, null, null);
        String avartar = null;
        while (cursor.moveToNext()) {
            avartar = cursor.getString(2);
        }

        return avartar;
    }

    public ArrayList<Integer> listIdUserOf(int idUser) {
        ArrayList<Integer> listUser = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query("follower", null, "iduser =?", new String[]{String.valueOf(idUser)}, null, null, null, null);
        while (cursor.moveToNext()) {
            listUser.add(cursor.getInt(2));
        }
        return listUser;
    }

    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


    // get id post
    public int getIDPostOf(int idUser) {
        Cursor cursor = sqLiteDatabase.query("post", null, "iduser = ?", new String[]{String.valueOf(idUser)}, null, null, null);
        int idPost = 0;
        while (cursor.moveToNext()) {
            idPost = cursor.getInt(0);
        }

        return idPost;
    }

    // get id comment

    public int getIDCommentOf(int idPost, int idUser) {

        Cursor cursor = sqLiteDatabase.query("comment", null, "iduser = ? and idpost = ?", new String[]{String.valueOf(idPost)}, String.valueOf(idUser), null, null);
        int idComment = 0;
        while (cursor.moveToNext()) {
            idComment = cursor.getInt(0);
        }

        return idComment;
    }

    public Boolean CheckNameinFollowing(int IDuserFollowing, int idCurrentUser) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from follower where idfollowing = ? and iduser = ?", new String[]{String.valueOf(IDuserFollowing), String.valueOf(idCurrentUser)});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    // insert notify
    public void insertNotify(int idUser, String content, String datime, int idPost, int idLike, int idcomment, int idShare, int idFollower) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("iduser", idUser);
        contentValues.put("content", content);
        contentValues.put("datetime", datime);
        contentValues.put("idpost", idPost);
        contentValues.put("idlike", idLike);
        contentValues.put("idcomment", idcomment);
        contentValues.put("idshare", idShare);
        contentValues.put("idfollower", idFollower);

        database.insert("notification", null, contentValues);
    }

    // remove notify

    public void RemoveNotify(int idNotify) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("notification", "id = ?", new String[]{String.valueOf(idNotify)});
    }

    public Cursor getPostsFromUser(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("post", null, "iduser =?", new String[]{String.valueOf(id)}, "id", null, "id desc");
    }

    public ArrayList<Integer> getListIDPost(int idUser) {
        ArrayList<Integer> listPost = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("post", null, "iduser = ? ", new String[]{String.valueOf(idUser)}, null, null, null);

        while (cursor.moveToNext()) {
            listPost.add(cursor.getInt(0));
        }

        return listPost;
    }
    public Post getPostFromID(int id, String nameUser){
        int idUser = getIduser(nameUser);
        User u = getUser(idUser);
        Post post;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("post", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if(c.moveToFirst()){
            post = new Post();
            post.setImgPost(c.getString(3));
            post.setContent(c.getString(2));
            post.setId(c.getInt(0));
            post.setIduser(idUser);
            post.setAvatar(getImagefor(idUser));
            post.setUsername(u.getName());
            post.setName(u.getName());
            post.setNumber_like("0");
            Calendar calendar = Calendar.getInstance();
            long time = calendar.getTimeInMillis();
            post.setTime(String.valueOf(time));
            return post;
        }
        return null;
    }

    @SuppressLint("Range")
    public Post getPost(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        Post post = null;
        Cursor cursor = database.query("post", null, "id = ?" , new String[]{String.valueOf(id)}, null, null, null, null);
        while (cursor.moveToNext()){
            post = new Post();
            User u = getUser(cursor.getInt(1));
            post.setImgPost(cursor.getString(3));
            post.setContent(cursor.getString(2));
            post.setId(cursor.getInt(0));
            post.setIduser(u.getId());
            post.setAvatar(getImagefor(cursor.getInt(1)));
            post.setUsername(u.getName());
            post.setName(u.getName());
            post.setNumber_like(String.valueOf(getLike(id).getCount()));
            post.setTime(cursor.getString(cursor.getColumnIndex("datetime")));
        }
        return post;
    }
    public long UpdatePost(Post p){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("iduser", p.getIduser());
        cv.put("content", p.getContent());
        cv.put("image", p.getImgPost());
        cv.put("like_count", "0");
        cv.put("comment_count", "0");
        cv.put("share_count", "0");
        cv.put("datetime", p.getTime());
        return db.update("post", cv, "id = ?", new String[]{String.valueOf(p.getId())});
    }

    public ArrayList<Integer> getIDofPostWhenClickFollow(int idUser) {
        ArrayList<Integer> listPost = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM post p JOIN follower f WHERE p.iduser = f.idfollowing and f.iduser = ?", new String[]{String.valueOf(idUser)});

        while (cursor.moveToNext()) {
            listPost.add(cursor.getInt(0));
        }

        return listPost;
    }

    public int CountFollowing(int idUser) {
        int count = 0;
        Cursor cursor = sqLiteDatabase.query("follower", null, "iduser = ?", new String[]{String.valueOf(idUser)}, null, null, null);
        while (cursor.moveToNext()) {
            count++;
        }

        return count;
    }

    public int CountMyFollower(int myID) {
        int count =0;
        Cursor cursor = sqLiteDatabase.query("follower", null, "idfollowing = ?" , new String[]{String.valueOf(myID)}, null, null, null);

        while (cursor.moveToNext()){
            count++;
        }

        return count;
    }

//     myDB.execSQL("create Table user(" +
//             "id Integer PRIMARY KEY NOT NULL UNIQUE," +
//             "name Text," +
//             "image Blob," +
//             "post_count Integer NOT NULL DEFAULT (0)," +
//             "follower_count Integer NOT NULL DEFAULT (0)," +
//             "following_count Integer NOT NULL DEFAULT (0)," +
//             "description  TEXT)");


    //check like
    public Boolean CheckLike(int idUser, int idPost) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.query("likes", null,"iduser = ? and idpost = ?", new String[]{String.valueOf(idUser), String.valueOf(idPost)},null,null,null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //insertLike
    public Boolean insertLikes(int iduser, int idpost) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("iduser", iduser);
        contentValues.put("idpost", idpost);
        long result = MyDB.insert("likes", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    // unlike
    public void Unlike(int iduser,int idpost) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("likes", "iduser = ? and idpost = ?" , new String[]{String.valueOf(iduser), String.valueOf(idpost)});
    }

    // getlike
    public Cursor getLike(int idPost){
        SQLiteDatabase db = getWritableDatabase();
        return db.query("likes", null,"idpost = ?", new String[] {String.valueOf(idPost)}, null, null, null);
    }
    public Cursor getLikeUser(int idPost){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("SELECT u.* FROM Likes l, Post p, user u WHERE l.idpost = p.id and idpost=? and l.iduser = u.id", new String[]{String.valueOf(idPost)});
    }
    public Post getPostFromID(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("post", null, "id =?", new String[]{String.valueOf(id)}, null, null, null, null);
        return new Post(c.getInt(0), c.getInt(1), getImgAvata(c.getInt(1)), c.getString(3), getName(c.getInt(1)), getName(c.getInt(1)), "0", c.getString(2), c.getString(7), c.getInt(8) == 1);
    }
}
