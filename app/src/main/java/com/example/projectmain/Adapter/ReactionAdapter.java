package com.example.projectmain.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.R;
import com.example.projectmain.Refactoring.Prototype.IReaction;
import com.example.projectmain.Refactoring.Prototype.Reaction;
import com.example.projectmain.Refactoring.Prototype.ReactionRegistry;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;
import com.example.projectmain.Refactoring.Strategy.OnItemClickListener;

import java.util.ArrayList;

public class ReactionAdapter extends RecyclerView.Adapter<ReactionAdapter.ReactionViewHolder> {
    Context c;
    int idUser;
    Cursor likes;
    int idPost;
    ArrayList<IReaction> reactions;
    ReactionRegistry reg = new ReactionRegistry();
    OnItemClickListener lisener;
    public void setOnReaction(OnItemClickListener lisener){
        this.lisener = lisener;
    }
    public ReactionAdapter(Context c, ArrayList<IReaction> reactions, int idPost) {
        this.c = c;
        this.reactions = reactions;
        this.idPost = idPost;
        idUser = GlobalUser.getInstance(c).getUser().getId();
    }

    @NonNull
    @Override
    public ReactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.reaction_view, parent, false);
        return new ReactionViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ReactionViewHolder holder, int position) {
        String emoji = reactions.get(position).getEmoji();
        DB db = new DB(c);
        holder.tvReaction.setText(emoji);
        likes = db.CheckLike(idUser, idPost);
        holder.tvReaction.setOnClickListener(v -> {
            if(likes.getCount() > 0 && likes.moveToNext()){
                IReaction reaction = reg.getByEmoji(likes.getString(4));
                if(reaction.getEmoji().equals(emoji)){
                    db.Unlike(idUser, idPost);
                } else {
                    db.editLike(idPost, reg.getByEmoji(emoji).getEmoji());
                }
            } else {
                db.insertLikes(idUser, idPost, emoji);
            }
            lisener.onClick(v);
        });
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
