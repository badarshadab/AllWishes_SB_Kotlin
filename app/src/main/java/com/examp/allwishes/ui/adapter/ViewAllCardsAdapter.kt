package com.examp.allwishes.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examp.allwishes.databinding.CreateCardsRowBinding
import com.examp.allwishes.databinding.ViewAllCardsRowBinding
import com.examp.allwishes.ui.model.Root_HlNew
import com.examp.allwishes.ui.util.AppUtils

class ViewAllCardsAdapter(
    val activity: Activity,
    val model: Root_HlNew,
    val mListener: RecyclerViewClickListener
) :
    RecyclerView.Adapter<ViewAllCardsAdapter.ContentPreviewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    private var rv: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentPreviewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val contentPreviewRowBinding =
            ViewAllCardsRowBinding.inflate(layoutInflater!!, parent, false)

        return ContentPreviewHolder(contentPreviewRowBinding)
    }

    override fun onBindViewHolder(holder: ContentPreviewHolder, position: Int) {
//        println("Shadab.ViewHolder " + position)
        val cIcon = model.createcards?.get(position)?.icon
        val cName = model.createcards?.get(position)?.name

        if (cIcon != null) {
            AppUtils.setImageWithRoundCorner(cIcon, holder.b.iv, 20, 300)
        }
        holder.b.tv.text = cName

        holder.b.iv.setOnClickListener { view ->
            mListener.onClick(view, position, cName)
        }


    }


    override fun getItemCount(): Int {
        return model.createcards!!.size
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    fun getTextView(index: Int): View? {
        val viewHolder = rv?.findViewHolderForAdapterPosition(index)
        viewHolder?.let {
            val holder = it as ContentPreviewHolder
            return holder.b.iv
        }
        return null
    }

    inner class ContentPreviewHolder(val b: ViewAllCardsRowBinding) :
        RecyclerView.ViewHolder(b.root) {

    }

    interface RecyclerViewClickListener {
        fun onClick(view: View?, position: Int, catName: String?)
    }
}