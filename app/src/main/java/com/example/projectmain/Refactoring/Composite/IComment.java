package com.example.projectmain.Refactoring.Composite;

import com.example.projectmain.Refactoring.FactoryMethod.Reaction;

public interface IComment {
    void reply(IComment comment);
    void interact(Reaction reaction);
    void delete();
    void edit(IComment newContent);
}
