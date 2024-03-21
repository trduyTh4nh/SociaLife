package com.example.projectmain.Refactoring.Prototype;

import android.content.Context;

import com.example.projectmain.Adapter.ReactionAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class ReactionRegistry implements IReactionRegistry{
    private final ArrayList<IReaction> reactions = new ArrayList<>(Arrays.asList(new Reaction("\uD83D\uDE02"), new Reaction("\uD83D\uDC4D"), new Reaction("\uD83D\uDE22"), new Reaction("❤\uFE0F")));
    @Override
    public void add(IReaction item) {
        reactions.add(item);
    }
    @Override
    public IReaction getByEmoji(String emoji) {
        for(IReaction reaction : reactions){
            if(reaction.getEmoji().equals(emoji)){
                return reaction.clone();
            }
        }
        return null;
    }
    @Override
    public ReactionAdapter prepareAdapter(Context context, int idPost){
        return new ReactionAdapter(context, reactions, idPost);
    }
}
