package com.example.projectmain.Refactoring.Builder;

import android.content.ContentValues;

public class ContentValueBuilder implements IPostContentValueBuilder{
    ContentValues _contentValues = new ContentValues();

    public void Reset(){
        this._contentValues = new ContentValues();
    }

    public ContentValues GetContentValue(){
        ContentValues result = this._contentValues;
        this.Reset();
        return result;
    }
    public ContentValueBuilder() {
    }

    @Override
    public IPostContentValueBuilder buildContent(String content) {
        _contentValues.put("content", content);
        return this;
    }

    @Override
    public IPostContentValueBuilder buildImage(String image) {
        _contentValues.put("image", image);
        return this;
    }

    @Override
    public IPostContentValueBuilder buidIsShare(int isShare) {
        _contentValues.put("isshare", isShare);
        return  this;
    }

    @Override
    public IPostContentValueBuilder buildTimeOfPost(String dateTime) {
        _contentValues.put("datetime", dateTime);
        return this;
    }

    @Override
    public IPostContentValueBuilder buildIdUser(int idUser) {
        _contentValues.put("iduser", idUser);
        return this;
    }

    @Override
    public ContentValues build() {
        return _contentValues;
    }


}
