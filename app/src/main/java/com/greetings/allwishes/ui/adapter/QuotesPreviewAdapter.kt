package com.greetings.allwishes.ui.adapter

import android.app.Activity
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greetings.allwishes.databinding.QuotesPreviewRowBinding

class QuotesPreviewAdapter(
    val activity: Activity,
    val list: List<String>
) :
    RecyclerView.Adapter<QuotesPreviewAdapter.QuotesPreviewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    private var rv: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesPreviewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val quotesPreviewRowBinding =
            QuotesPreviewRowBinding.inflate(layoutInflater!!, parent, false)
        return QuotesPreviewHolder(quotesPreviewRowBinding)
    }

    override fun onBindViewHolder(holder: QuotesPreviewHolder, position: Int) {
        holder.setData(list[position])
//        println("Shadab.ViewHolder " + position)

//        val text = list[position].toString()
//        holder.b.tv.setText(text)
    }

//    fun setImageList(list: List<Any>) {
//        this.list.clear()
//        this.list.addAll(list)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItem(index: Int): String {
        return list[index]
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    fun getTextView(index: Int): View? {
        val viewHolder = rv?.findViewHolderForAdapterPosition(index)
        viewHolder?.let {
            val holder = it as QuotesPreviewHolder
            return holder.b.tv
        }
        return null
    }

    inner class QuotesPreviewHolder(val b: QuotesPreviewRowBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun setData(s: String) {

            val typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/quotesfont.TTF")
            b.tv.setTypeface(typeface)
            b.tv.text = s
        }
    }
}