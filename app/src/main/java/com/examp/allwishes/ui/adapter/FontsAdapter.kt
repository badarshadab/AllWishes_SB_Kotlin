package com.examp.allwishes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.examp.allwishes.R
import com.examp.allwishes.ui.util.OnItemClickListener
import com.modlueinfotech.allwishesgif.data.model.createCardMode.FontsModel


class FontsAdapter(
    val fontlist: ArrayList<FontsModel>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FontsAdapter.MyViewHolder>() {
    class MyViewHolder(view: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        val textstyle: TextView = view.findViewById(R.id.textstylerowitem)

        init {
            view.setOnClickListener {
                onItemClickListener.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.fontsitem, parent, false)
        return MyViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return fontlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val FontsModel = fontlist[position].fontstyle
        val face3 = ResourcesCompat.getFont(context, fontlist[position].fontstyle!!);
        holder.textstyle.setTypeface(face3)

        val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.createcardfontsanim)
        holder.textstyle.startAnimation(anim)
    }
}