package com.greetings.allwishes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.greetings.allwishes.R
import com.greetings.allwishes.ui.model.ColorModel
import com.greetings.allwishes.ui.util.OnItemClickListener

class TextChooseColorAdapter(
    val list: List<ColorModel>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TextChooseColorAdapter.MyViewHolder>() {


    class MyViewHolder(view: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        val tcolorview: ImageView = view.findViewById(R.id.textcolorimgview)

        init {
            view.setOnClickListener {
                onItemClickListener.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.textcolorlayout, parent, false)
        return MyViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == 0) {
            holder.tcolorview.setImageResource(R.drawable.colorimg)
        } else {
            holder.tcolorview.setBackgroundColor(list[position].color)


        }
    }
}