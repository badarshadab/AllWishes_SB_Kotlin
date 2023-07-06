package com.greetings.allwishes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greetings.allwishes.R
import com.greetings.allwishes.ui.util.StickerOnItemClick
import com.google.firebase.storage.StorageReference

class StickerAdpter(
    val stickerslist: List<StorageReference>,
    var context: Context,
    var onItemClickListener: StickerOnItemClick
) : RecyclerView.Adapter<StickerAdpter.MyViewModel>() {


    class MyViewModel(view: View, onItemClickListener: StickerOnItemClick) :
        RecyclerView.ViewHolder(view) {

        var stickerview: ImageView = view.findViewById(R.id.stickerview)

        init {
            view.setOnClickListener {
                onItemClickListener.onClick(view, adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.stickerrawitem, parent, false)
        return MyViewModel(view, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return stickerslist.size
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {

        Glide.with(context).load(stickerslist[position]).placeholder(R.drawable.loading_img)
            .into(holder.stickerview)

    }
}