package com.examp.allwishes.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examp.allwishes.R
import com.examp.allwishes.ui.model.CreateCards
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.util.OnItemClickListener
import com.google.firebase.storage.StorageReference

class AddbgCardAdapter(
    var activity: Activity,
    val list: List<StorageReference>,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AddbgCardAdapter.MyViewHolder>() {

    class MyViewHolder(view: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        val gmimageview: ImageView = itemView.findViewById(R.id.addbgimageview)

        init {
            itemView.setOnClickListener {
                onItemClickListener.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.addbgcarditem, parent, false)
        return MyViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        AppUtils.setImage(holder.gmimageview , list[position])
//        Glide.with(activity).load(model[position]).centerCrop()
//            .placeholder(R.drawable.loading_img)
//            .into(holder.gmimageview);

    }
}