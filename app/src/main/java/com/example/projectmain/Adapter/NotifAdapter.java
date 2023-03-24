package com.example.projectmain.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Model.NotifClass;
import com.example.projectmain.R;

import java.util.ArrayList;
import java.util.Comparator;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {
    @NonNull
    @Override
    public NotifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.notif_box, parent, false);


        return new NotifAdapter.ViewHolder(v);
    }

    public NotifAdapter(Context c, ArrayList<NotifClass> n) {
        notifList = n;
        context = c;
    }

    private ArrayList<NotifClass> notifList;
    TextView adTvName;
    TextView adTvMsg;
    TextView adTvT;
    ImageView adIvAv;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adTvName = itemView.findViewById(R.id.tvName);
            adTvMsg = itemView.findViewById(R.id.tvMsg);
            adTvT = itemView.findViewById(R.id.tvTime);
            adIvAv = itemView.findViewById(R.id.ivAvt);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.ViewHolder holder, int position) {
        NotifClass n = notifList.get(position);
        int day = n.getHour() / 24;
        adTvName.setText(n.getName());
        adTvMsg.setText(n.getMessage());
        if (day < 1) {
            adTvT.setText(n.getHour() + " giờ trước");
        } else if (day > 14) {
            adTvT.setText(day / 7 + " tuần trước");
        } else if (day / 7 > 5) {
            adTvT.setText(day / 30 + " tháng trước");
        } else {
            adTvT.setText(day + " ngày trước");
        }
        adIvAv.setImageResource(n.getImg());
    }

    @Override
    public int getItemCount() {
        return notifList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notifList.sort(Comparator.comparing(NotifClass::getHour));
        }
        return 1;
    }
}
