package com.example.projectmain.Refactoring.Prototype;

import android.content.Context;

import com.example.projectmain.Adapter.ReactionAdapter;

public interface IReactionRegistry {
    void add(IReaction item);
    IReaction getByEmoji(String emoji);
    ReactionAdapter prepareAdapter(Context context);
}
