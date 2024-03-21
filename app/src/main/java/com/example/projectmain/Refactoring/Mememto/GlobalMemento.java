package com.example.projectmain.Refactoring.Mememto;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.projectmain.Refactoring.Singleton.GlobalUser;

import java.util.ArrayList;

public class GlobalMemento
{
    private static GlobalMemento INSTANCE_MEM;
    private ArrayList<PostMemento> arrs = new ArrayList<>();

    public GlobalMemento( ArrayList<PostMemento> arrs) {
        this.arrs = arrs;
    }
    public GlobalMemento(){
        arrs = new ArrayList<>();
    }

    public  ArrayList<PostMemento> getArrs() {
        return arrs;
    }

    public void setArrs( ArrayList<PostMemento> arrs) {
        this.arrs = arrs;
    }

    public static GlobalMemento getInstance( ArrayList<PostMemento> list ) {
            INSTANCE_MEM = new GlobalMemento(list);
            return INSTANCE_MEM;
    }
    public static GlobalMemento getInstance( ) {
        if(INSTANCE_MEM == null){
            INSTANCE_MEM = new GlobalMemento();
        }
        return INSTANCE_MEM;
    }

    @NonNull
    @Override
    public String toString() {
        return arrs.toString();
    }
}
