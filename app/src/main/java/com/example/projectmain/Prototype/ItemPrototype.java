package com.example.projectmain.Prototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectmain.R;

public class ItemPrototype implements Prototype{

    private ImageView imageView;
    private TextView title;
    private View view;

    public ItemPrototype( Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_setting, null);
        this.imageView = view.findViewById(R.id.img_Icon);
        this.title = view.findViewById(R.id.tv_title);

    }
    public void SetContent(int imageView, String title)
    {
        this.imageView.setImageResource(imageView);
        this.title.setText(title);
    }
    public View getView() {
        return view;
    }
    public void setOnClickListener(View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }
    @Override
    public Prototype clone() {
        return new ItemPrototype(view.getContext());
    }
}
