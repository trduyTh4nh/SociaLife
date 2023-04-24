package com.example.projectmain.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.projectmain.Adapter.NotifAdapter;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.NotifClass;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class NotifyFragment extends Fragment implements View.OnClickListener {

    private String Username;
    ArrayList<NotifClass> listImage;
    DB db;
    User u;
    SharedPreferences sharedPreferences;
    private static final String SHARE_PRE_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    public NotifyFragment() {

    }

    public static NotifyFragment newInstance(String param1, String param2) {
        NotifyFragment fragment = new NotifyFragment();
        Bundle args = new Bundle();
        args.putString("Username", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notify, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//
//        Random r = new Random();
//        NotifClass[] Names = {
//                new NotifClass("Đen Vâu", ""),
//                new NotifClass("Gia Đường", ""),
//                new NotifClass("RPT Origin", ""),
//                new NotifClass("Elon Musk", ""),
//        };
//        String[] Msgs = {"Đã follow bạn", "Đã thích bài viết của bạn", "Đã bình luận"};
//
//        listImage = new ArrayList<NotifClass>();
//        for (int i = 1; i <= 10; i++) {
//            int rndP = r.nextInt(Names.length - 1);
//            int rndM = r.nextInt(Msgs.length - 1);
//            int rnd = r.nextInt(24 * 100);
//            listImage.add(new NotifClass(Names[rndP].getName(), Msgs[rndM], rnd, Names[rndP].getImg()));
//
//        }

    }

    NotifAdapter adapter;
    ImageButton fBtnClear;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DB(getContext());
        RecyclerView re = view.findViewById(R.id.rcvNotif);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        sharedPreferences = getActivity().getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        u = db.getUser(email);
        int idUser = u.getId();


        listImage = getNotifyList(idUser);
        for (int i = 0; i < listImage.size(); i++) {
            Log.d("Value", listImage.get(i).getName());
        }

        adapter = new NotifAdapter(getActivity().getApplicationContext(), listImage);
        re.setAdapter(adapter);
        re.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });

        ItemTouchHelper.SimpleCallback callBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                int pos = viewHolder.getAdapterPosition();
                int idUser = u.getId();
                db.RemoveNotify(pos, idUser);
                Log.d("index: ", String.valueOf(pos));

                adapter.notifyItemRemoved(pos);
                listImage.remove(pos);
                Snackbar.make(view.findViewById(R.id.rcvNotif), "Đã xóa thông báo", Snackbar.LENGTH_LONG).show();
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callBack);
        touchHelper.attachToRecyclerView(re);
        fBtnClear = getView().findViewById(R.id.btnWipe);
        fBtnClear.setOnClickListener(this);
    }

//    public void removeAt(int position) {
//        listImage.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mDataSet.size());
//    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View v) {
        listImage.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        String email = sharedPreferences.getString(KEY_EMAIL, null);

        u = db.getUser(email);
        int idUser = u.getId();
//        ArrayList<NotifClass> list = new ArrayList<NotifClass>();
//        SQLiteDatabase database = db.getWritableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM post p JOIN follower f on p.iduser = f.idfollowing WHERE f.iduser = ?", new String[]{String.valueOf(idUser)});
//
//        while (cursor.moveToNext()) {
//            String userName = db.getName(cursor.getInt(1));
//            int idUserFollower = db.getIduser(userName);
//
//            String message = userName + " đã đăng một bài viết";
//            int time = cursor.getInt(7);
//            String avatar = cursor.getString(3);
//            int idPost = db.getIDPostOf(idUserFollower);
//            // chưa có chức năng share nên set id mặt định bằng 0;
//            // chưa có chức năng like nên set id mặt định bằng 0;
//
//
//            int idShare = 0;
//            int idLike = 0;
//            int idComment = db.getIDCommentOf(idPost, idUserFollower);
//            //int idFollower =
//            db.insertNotify(idUser, message, String.valueOf(time), idPost, idLike, idComment, idShare, idUserFollower);
    }


    // làm lại hàm insert sao đó lưu list trong table notify và render ra giao diện
// nó sẽ insert vào cái mà mình lấy bằng hàm bên database đem qua rồi insert vào table notify chứ k duyệt và insert bằng table post
// muốn lấy avatar thì dùng hàm get Avatar bên database
// sau đó nghĩ cách viết hàm lấy các id còn lại, và user name + message, id comment
// lưu ý idshare, idlike, time = 0, vì chưa có chức năng
//    public ArrayList<NotifClass> listNotify(int idCurrentUser) {
//        ArrayList<NotifClass> list = new ArrayList<NotifClass>();
//        SQLiteDatabase database = db.getWritableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM post p JOIN follower f on p.iduser = f.idfollowing WHERE f.iduser = ?", new String[]{String.valueOf(idCurrentUser)});
//
//        while (cursor.moveToNext()) {
//            String userName = db.getName(cursor.getInt(1));
//            int idUserFollower = db.getIduser(userName);
//            String message = userName + " đã đăng một bài viết";
//            int time = cursor.getInt(7);
//            String avatar = cursor.getString(3);
//            list.add(new NotifClass(userName, message, time, avatar));
//
//
//        }
//
//
//        return list;
//    }

    public ArrayList<NotifClass> getNotifyList(int myID) {
        ArrayList<NotifClass> notifClasses = new ArrayList<>();
        SQLiteDatabase database = db.getWritableDatabase();


        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM notification n  JOIN follower f  on n.iduser = f.idfollowing and f.iduser = ?", new String[]{String.valueOf(myID)});
            while (cursor.moveToNext()) {
                String name = db.getName(cursor.getInt(1));
                String curTime = "0 0 0 0";
                try {
                    curTime = cursor.getString(3);
                } catch (ArrayIndexOutOfBoundsException e) {
                    curTime = "E R R O R !";
                }
                String content = cursor.getString(2);
                String avatar = db.getImagefor(cursor.getInt(1));
                String[] times = curTime.split(" ");

                String CuTime = "ERROR!";
                if(times.length == 4){
                    CuTime = times[0] + " " + times[1] + " " + times[2] + " " + times[3];
                }
                notifClasses.add(new NotifClass(name, content, CuTime, avatar));
                Log.d("Time", CuTime);

            }
        } finally {

            if (cursor != null)
                cursor.close();
        }
        return notifClasses;
    }
}



