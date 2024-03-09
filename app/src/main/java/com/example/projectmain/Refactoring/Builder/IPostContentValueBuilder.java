package com.example.projectmain.Refactoring.Builder;

import android.content.ContentValues;

public interface IPostContentValueBuilder {
    IPostContentValueBuilder buildContent(String content);
    IPostContentValueBuilder buildImage(String image);
    IPostContentValueBuilder buidIsShare(int isShare);
    IPostContentValueBuilder buildTimeOfPost(String dateTime);
    IPostContentValueBuilder buildIdUser(int idUser);

    ContentValues build();

}
