    package com.example.projectmain.Refactoring.Mememto;

    import android.util.Log;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Stack;

    public class PostHistory {
        private final ArrayList<PostMemento> history = new ArrayList<>();
        private ArrayList<PostMemento> arrs = new ArrayList<>();

        public  ArrayList<PostMemento> getArrs() {
            return arrs;
        }

        public ArrayList<PostMemento> getHistory() {
            return history;
        }

        public void save(PostMemento memento){
            this.history.add(memento);
            PostMemento[] mems = new PostMemento[]{};

            // Sao chép các phần tử từ Stack vào mảng

            arrs = new ArrayList<PostMemento>(Arrays.asList(history.toArray(mems)));
            Log.d("arra", "save: " + arrs);
        }

        public PostMemento undo(int position){
            if (!history.isEmpty()){
                return history.remove(position);
            }
            return null;
        }
    }
