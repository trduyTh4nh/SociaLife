package com.example.projectmain.Refactoring.Builder;

import android.content.ContentValues;
import android.net.Uri;
import android.telephony.data.UrspRule;
import android.util.Log;

import java.util.Calendar;

public class Director {
    private IPostContentValueBuilder _builder;

    public Director(IPostContentValueBuilder builder, int idUser) {
        this._builder = builder;
        this._builder.buildIdUser(idUser);
    }
    public IPostContentValueBuilder get_builder() {
        return _builder;
    }

    public void set_builder(IPostContentValueBuilder _builder) {
        this._builder = _builder;
    }

    Calendar c = Calendar.getInstance();
    long t = c.getTimeInMillis();

    public ContentValues buildOnlyTextPost(String content, int isShare){
        return _builder.buildContent(content)
                .buidIsShare(isShare)
                .buildTimeOfPost(String.valueOf(t))
                .build();
    }

    public ContentValues builOnlyImagePost(Uri image, int isShae){
        return _builder.buildImage(String.valueOf(image))
                .buidIsShare(isShae)
                .build();
    }

    public ContentValues buildImageAndContentPost(Uri image, String content, int isShare){
        return _builder.buildContent(content)
                .buildImage(String.valueOf(image))
                .buidIsShare(isShare)
                .buildTimeOfPost(String.valueOf(t))
                .build();
    }



}
