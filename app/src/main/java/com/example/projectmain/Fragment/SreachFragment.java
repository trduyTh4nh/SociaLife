package com.example.projectmain.Fragment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.projectmain.Database.DB;
import com.example.projectmain.MainActivity;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;

import java.util.ArrayList;
import java.util.List;

public class SreachFragment extends Fragment {


    DB db;
    AutoCompleteTextView sview;

    ArrayList<User> arrUser = new ArrayList<User>();
    ArrayAdapter<String> a;
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

        db = new DB(getContext().getApplicationContext());

        sview = (AutoCompleteTextView) view.findViewById(R.id.searchView);

        List<String> listName = db.getListName();

        String[] names = new String[listName.size()];
        for (int i =0 ;i < names.length;i++){
            names[i] = listName.get(i);
        }
        a = new ArrayAdapter<String>(getContext(), R.layout.item_list_searchview, listName);
        sview.setAdapter(a);
        a.notifyDataSetChanged();
        sview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //tvRead.setText(autoCl.getText().toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


}