package com.example.projectmain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectmain.Model.Category;
import com.example.projectmain.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {


    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_selected,parent,false);
        TextView tv_selected=convertView.findViewById(R.id.tv_selected);
        Category category=this.getItem(position);
        if(category!=null)
        {
            tv_selected.setText(category.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        TextView tv_category=convertView.findViewById(R.id.tv_category);
        Category category=this.getItem(position);
        if(category!=null)
        {
            tv_category.setText(category.getName());
        }
        return convertView;
    }
}
