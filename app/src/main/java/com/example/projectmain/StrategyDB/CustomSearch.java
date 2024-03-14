package com.example.projectmain.StrategyDB;

import android.database.Cursor;

import com.example.projectmain.StrategyDB.ISearchStrategy;

public class CustomSearch {
    private ISearchStrategy searchStrategy;
    public CustomSearch(ISearchStrategy strategy) {
        this.searchStrategy = strategy;
    }

    public Cursor performSearch(String keyword) {
        return searchStrategy.search(keyword);
    }
}
