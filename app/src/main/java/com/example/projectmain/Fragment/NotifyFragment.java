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
                db.RemoveNotify(listImage.get(pos).getID());
                adapter.notifyItemRemoved(pos);
                listImage.remove(pos);
                Toast.makeText(getContext().getApplicationContext(), "Đã xóa thông báo", Toast.LENGTH_SHORT).show();
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

    }


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

                String[] times = curTime.split(" ");

                String CuTime = "ERROR!";
                if (times.length >= 4) {
                    CuTime = times[0] + " " + times[1] + " " + times[2] + " " + times[3];
                }
                String content = cursor.getString(2);
                String avatar = db.getImgAvata(cursor.getInt(1));
                notifClasses.add(new NotifClass(cursor.getInt(0), name, content, avatar, CuTime));
                Log.d("Time", avatar);

            }
        } finally {

            if (cursor != null)
                cursor.close();
        }
        return notifClasses;
    }
}



