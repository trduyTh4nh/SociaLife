package com.example.projectmain.Refactoring.Singleton;

import com.example.projectmain.Refactoring.Prototype.IReaction;
import com.example.projectmain.Refactoring.Prototype.IReactionRegistry;
import com.example.projectmain.Refactoring.Prototype.ReactionRegistry;

public final class GlobalReactionRegistry {
    private final IReactionRegistry registry;
    private static GlobalReactionRegistry instance;
    private GlobalReactionRegistry(){
        registry = new ReactionRegistry();
    }

    public IReactionRegistry getRegistry() {
        return registry;
    }

    public static GlobalReactionRegistry getInstance() {
        if(instance == null){
            instance = new GlobalReactionRegistry();
            return instance;
        }
        return instance;
    }
}
