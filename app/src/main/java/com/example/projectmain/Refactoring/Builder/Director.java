package com.example.projectmain.Refactoring.Builder;

import android.content.ContentValues;
import android.net.Uri;
import android.telephony.data.UrspRule;
import android.util.Log;

import java.util.Calendar;

public class Director {
    private ContentValueBuilder _ContentBuilder;

    public Director(ContentValueBuilder builder, int idUser) {
        this._ContentBuilder = builder;
        this._ContentBuilder.buildIdUser(idUser);
    }
    public ContentValueBuilder get_ContentBuilder() {
        return _ContentBuilder;
    }

    public void set_ContentBuilder(ContentValueBuilder _ContentBuilder) {
        this._ContentBuilder = _ContentBuilder;
    }

    Calendar c = Calendar.getInstance();
    long t = c.getTimeInMillis();
    public ContentValues buildOnlyTextPost(String content, int isShare){
        return _ContentBuilder.buildContent(content)
                .buidIsShare(isShare)
                .buildTimeOfPost(String.valueOf(t))
                .build();
    }

    public ContentValues builOnlyImagePost(Uri image, int isShae){
        return _ContentBuilder.buildImage(String.valueOf(image))
                .buidIsShare(isShae)
                .build();
    }

    public ContentValues buildImageAndContentPost(Uri image, String content, int isShare){
        return _ContentBuilder.buildContent(content)
                .buildImage(String.valueOf(image))
                .buidIsShare(isShare)
                .buildTimeOfPost(String.valueOf(t))
                .build();

    }


}
