package com.example.projectmain.Refactoring.Decorator;
import android.content.Context;

public abstract class Decorator implements IDecorator{
    protected  IDecorator decoratorWrap;
    public Decorator(IDecorator decorator){
        this.decoratorWrap = decorator;
    }
    @Override
    public void addItem(Context context) {
        if (decoratorWrap != null) {
            decoratorWrap.addItem(context);
        }
    }
}
