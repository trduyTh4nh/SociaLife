package com.example.projectmain.Refactoring.Prototype;

import androidx.annotation.NonNull;

public interface IReaction {
    String getEmoji();
    @NonNull
    IReaction clone();
}
