package com.greetings.allwishes.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greetings.allwishes.R;
import com.greetings.allwishes.ui.model.DailyWishe;
import com.greetings.allwishes.ui.util.AppUtils;

import java.util.ArrayList;


public class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<DailyWishe> model;
    Activity activity;
    private RecyclerViewClickListener mListener;


    public BannerAdapter(Activity activity, ArrayList<DailyWishe> model, RecyclerViewClickListener mListener) {
        this.model = model;
        this.activity = activity;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
//        String cIcon = model.getIcon();
//        String cName = model.getTop().get(position).getName();
//        myViewHolder.setImage(cIcon);
//        myViewHolder.tv.setText(cName);
        DailyWishe dailyWishe = model.get(position);
        myViewHolder.setImage(dailyWishe.getIcon());
        myViewHolder.tv.setText(dailyWishe.getName());

        myViewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position);
//                Toast.makeText(activity, "clicked on  " + cName, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv;
        private RecyclerViewClickListener mListener;

        MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }

        public void setImage(String url) {
            AppUtils.INSTANCE.setImage(iv ,url);
        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }
}
