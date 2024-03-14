package com.example.projectmain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.R;
import com.example.projectmain.Refactoring.Prototype.IReaction;

import java.util.ArrayList;

public class ReactionAdapter extends RecyclerView.Adapter<ReactionAdapter.ReactionViewHolder> {
    Context c;
    ArrayList<IReaction> reactions;


    public ReactionAdapter(Context c, ArrayList<IReaction> reactions) {
        this.c = c;
        this.reactions = reactions;
    }

    @NonNull
    @Override
    public ReactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.reaction_view, parent, false);
        return new ReactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReactionViewHolder holder, int position) {
        holder.tvReaction.setText(reactions.get(position).getEmoji());
    }

    @Override
    public int getItemCount() {
        return reactions.size();
    }

    public static class ReactionViewHolder extends RecyclerView.ViewHolder{
        public TextView tvReaction;
        public ReactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReaction = itemView.findViewById(R.id.txtEmoji);

        }
    }
}
