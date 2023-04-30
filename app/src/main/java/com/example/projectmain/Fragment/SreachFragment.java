package com.example.projectmain.Fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.projectmain.Adapter.UserSearchAdapter;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;

import java.util.ArrayList;
import java.util.List;

public class SreachFragment extends Fragment {


    public static DB db;
    AutoCompleteTextView sview;

    ArrayList<User> arrUser = new ArrayList<User>();
    ArrayAdapter<String> a;
    RecyclerView r;
    TextView tvSearch;
    public SreachFragment() {

    }


    public static SreachFragment newInstance(String param1, String param2) {
        SreachFragment fragment = new SreachFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sreach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        r = view.findViewById(R.id.rcvSearch);
        db = new DB(getContext().getApplicationContext());
        tvSearch = view.findViewById(R.id.tvResultCount);
        sview = (AutoCompleteTextView) view.findViewById(R.id.searchView);

        List<String> listName = db.getListName();

        String[] names = new String[listName.size()];

        for (int i =0 ;i < names.length;i++){
            names[i] = listName.get(i);
        }
        a = new ArrayAdapter<String>(getContext(), R.layout.item_list_searchview, listName);
        sview.setAdapter(a);
        a.notifyDataSetChanged();

        UserSearchAdapter adap = new UserSearchAdapter(getActivity(), arrUser);
        LinearLayoutManager g = new LinearLayoutManager(view.getContext());
        r.setLayoutManager(g);
        r.setAdapter(adap);
        a.notifyDataSetChanged();
        sview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //tvRead.setText(autoCl.getText().toString());

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(sview.getText().toString().equals("")){
                    tvSearch.setText("Vui lòng nhập từ khóa.");
                    arrUser.clear();
                    adap.notifyDataSetChanged();
                    return;
                }
                updateData(sview.getText().toString());
                adap.notifyDataSetChanged();
                tvSearch.setText("Đang hiển thị " + arrUser.size() + " kết quả khớp với từ khóa \"" + sview.getText().toString() + "\"");
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
    @SuppressLint("Range")
    void updateData(String k){

        if(arrUser != null){
            arrUser.clear();
        } else {
            arrUser = new ArrayList<User>();
        }
        Cursor c = db.getUserFromSearch(k);

        while(c.moveToNext()){
            arrUser.add(new User(c.getInt(c.getColumnIndex("id")), c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("description"))));
        }
        Log.d("Length", String.valueOf(arrUser.size()));
    }


}