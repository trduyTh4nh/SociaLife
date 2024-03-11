package com.example.projectmain.StrategyDB;

import android.content.Context;
import android.database.Cursor;

import com.example.projectmain.Database.DB;
import com.example.projectmain.StrategyDB.ISearchStrategy;

public class SearchByContent implements ISearchStrategy {

    private Context mContext;

    public SearchByContent(Context context) {
        this.mContext = context;
    }
    @Override
    public Cursor search(String keyword) {
        DB db = new DB(mContext);
        return db.getPostFromSearch(keyword);
    }
}
