package com.example.projectmain.StrategyDB;

import android.database.Cursor;

public interface ISearchStrategy {

        Cursor search(String keyword);

}
