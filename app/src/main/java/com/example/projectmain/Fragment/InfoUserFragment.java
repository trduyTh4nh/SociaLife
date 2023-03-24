package com.example.projectmain.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.projectmain.R;

import java.util.Calendar;

public class InfoUserFragment extends Fragment {


    private String mParam1;
    private String mParam2;

    public InfoUserFragment() {

    }


    public static InfoUserFragment newInstance(String param1, String param2) {
        InfoUserFragment fragment = new InfoUserFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_info_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText birthday = (EditText) getView().findViewById(R.id.edtNgaySinh);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Log.e("Ngày sinh: ", "Ngày: " + dayOfMonth + " Tháng: " + month + " Năm: " + year);
                            birthday.setText(month + "/" + dayOfMonth + "/" + year);
                        }
                    }, year, month, day);

                    datePickerDialog.setTitle("Chọn ngày");
                    datePickerDialog.show();
                }

            }
        });
    }
}