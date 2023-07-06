package com.greetings.allwishes.adapters.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greetings.allwishes.R
import com.greetings.allwishes.ui.util.OnItemClickListener_Gif
import com.google.firebase.storage.StorageReference

class CardAdapter(
    var list: List<StorageReference>,
    var context: Context,
    private val onitemclicklistenerGif: OnItemClickListener_Gif
) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    inner class ViewHolder(view: View, onitemclicklistenerGif: OnItemClickListener_Gif) :
        RecyclerView.ViewHolder(view) {
        val fest_imageView: ImageView = itemView.findViewById(R.id.row_item_gif_card)

        init {
            itemView.setOnClickListener {
                onitemclicklistenerGif.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.gif_and_card_row_item, parent, false)
        return ViewHolder(view, onitemclicklistenerGif)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide
            .with(context)
            .load(list[position])
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.error_img)
            .into(holder.fest_imageView)

//        holder.fest_imageView.setImageResource(list[position].img_and_cards!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}