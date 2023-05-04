package com.examp.allwishes.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.examp.allwishes.R;
import com.examp.allwishes.ui.model.Root_HlNew;
import com.examp.allwishes.ui.util.AppUtils;


public class BannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Root_HlNew model;
    Activity activity;
    private RecyclerViewClickListener mListener;


    public BannerAdapter(Activity activity, Root_HlNew model, RecyclerViewClickListener mListener) {
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
        String cIcon = model.getDailyWishes().get(position).getIcon();
        String cName = model.getDailyWishes().get(position).getName();
//        myViewHolder.setImage(cIcon);
        Glide.with(activity)
                .load(cIcon)
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.error_img)
                .into(myViewHolder.iv);
        AppUtils.INSTANCE.setScaleAnimation(holder.itemView);
        myViewHolder.tv.setText(cName);
        myViewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position, cName);
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.getDailyWishes().size();
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
//            System.out.println("setImage(String url)   " + url);
//            AppUtils.INSTANCE.setImage(iv, url);

        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position, String catName);
    }
}
