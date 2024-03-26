package com.example.projectmain.Refactoring.Prototype;

import androidx.annotation.NonNull;

public class Reaction implements IReaction{
    private final String emoji;

    public Reaction(String emoji) {
        this.emoji = emoji;
    }
    public Reaction(IReaction copyOfReaction){
        this.emoji = copyOfReaction.getEmoji();
    }
    @Override
    public String getEmoji() {
        return emoji;
    }

    @NonNull
    @Override
    public IReaction clone() {
        return new Reaction(this);
    }
}
