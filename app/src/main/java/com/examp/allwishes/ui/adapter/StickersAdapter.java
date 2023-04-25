package com.examp.allwishes.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examp.allwishes.ui.util.AppUtils;
import com.examp.allwishes.ui.util.RecyclerViewClickListener;
import com.examp.allwishes.R;
import com.examp.allwishes.ui.util.AppUtils;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class StickersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerViewClickListener mListener;
    private List<StorageReference> list;

    public StickersAdapter(List<StorageReference> list, RecyclerViewClickListener mListener) {
        this.list = list;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sticker_row, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        AppUtils.INSTANCE.setImage(myViewHolder.imageView, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        private RecyclerViewClickListener mListener;

        MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }
}
