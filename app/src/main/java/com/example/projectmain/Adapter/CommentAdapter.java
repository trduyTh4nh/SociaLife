package com.example.projectmain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Class.Commentclass;
import com.example.projectmain.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    public CommentAdapter(Context context, ArrayList<Commentclass> c){
        this.context = context;
        cmtList = c;
    }
    private TextView tvUser, tvContent, tvTime, tvLikes;
    private Context context;
    ArrayList<Commentclass> cmtList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Commentclass cmt = cmtList.get(position);
        tvUser.setText(cmt.getCommenter());
        tvContent.setText(cmt.getCommentcontent());
        int time = cmt.getTime();
        if(time > 24)
            tvTime.setText(time/24 + " Ngày trước");
        else
            tvTime.setText(time + " Giờ trước");
        int like = cmt.getLikes();
        if(like > 1000){
            tvLikes.setText(like / 1000 + "K");
        } else {
            tvLikes.setText(String.valueOf(cmt.getLikes()));
        }
    }

    @Override
    public int getItemCount() {
        return cmtList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTimeCmt);
            tvLikes = itemView.findViewById(R.id.tvLikeCount);
        }
    }
}
