package com.example.projectmain.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.projectmain.Adapter.NotifAdapter;
import com.example.projectmain.Class.NotifClass;
import com.example.projectmain.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifyFragment extends Fragment implements View.OnClickListener{

    private String Username;

    public NotifyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NotifyFragment newInstance(String param1, String param2) {
        NotifyFragment fragment = new NotifyFragment();
        Bundle args = new Bundle();
        args.putString("Username", param1);
        fragment.setArguments(args);
        return fragment;
    }
    ArrayList<NotifClass> n;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notify, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random r = new Random();

            NotifClass[] Names = {
                    new NotifClass("Đen Vâu", R.drawable.den),
                    new NotifClass("Gia Đường", R.drawable.duong),
                    new NotifClass("RPT Origin", R.drawable.origin),
                    new NotifClass("Elon Musk", R.drawable.elon),
            };
            String[] Msgs = {"Đã follow bạn", "Đã thích bài viết của bạn", "Đã bình luận"};

            n = new ArrayList<NotifClass>();
            for (int i = 1; i <= 10; i++) {
                int rndP = r.nextInt(Names.length - 1);
                int rndM = r.nextInt(Msgs.length - 1);
                int rnd = r.nextInt(24 * 100);
                n.add(new NotifClass(Names[rndP].getName(), Msgs[rndM], rnd, Names[rndP].getImg()));

        }

    }
    NotifAdapter adapter;
    ImageButton fBtnClear;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView re = getView().findViewById(R.id.rcvNotif);
        adapter = new NotifAdapter(getActivity().getApplicationContext(), n);
        re.setAdapter(adapter);
        re.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()){
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
                n.remove(pos);
                String name = n.get(pos).getName();
                adapter.notifyItemRemoved(pos);
                Snackbar.make(getView().findViewById(R.id.rcvNotif), "Đã xóa thông báo từ " + name, Snackbar.LENGTH_LONG).show();
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callBack);
        touchHelper.attachToRecyclerView(re);
        fBtnClear = getView().findViewById(R.id.btnWipe);
        fBtnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        n.clear();
        adapter.notifyDataSetChanged();
    }
}