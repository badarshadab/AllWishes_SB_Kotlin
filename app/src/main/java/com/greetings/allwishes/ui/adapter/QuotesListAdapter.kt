package com.greetings.allwishes.ui.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greetings.allwishes.databinding.QoutesListRowLayoutBinding

class QuotesListAdapter(
    val activity: Activity,
    val list: List<Any>,
    private var mListener: RecyclerViewClickListener? = null
) :
    RecyclerView.Adapter<QuotesListAdapter.ContentPreviewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    private var rv: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentPreviewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val qoutesListRowLayoutBinding =
            QoutesListRowLayoutBinding.inflate(layoutInflater!!, parent, false)


        return ContentPreviewHolder(qoutesListRowLayoutBinding)
    }

    override fun onBindViewHolder(holder: ContentPreviewHolder, position: Int) {


        if (position % 2 == 0) {
            holder.b.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.b.card.setCardBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }

        holder.b.quotesText.text = list.get(position).toString()
        holder.b.quotesText.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mListener?.onClick(v, position)
            }

        })
    }


    override fun getItemCount(): Int {
        return list.size
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }


    inner class ContentPreviewHolder(val b: QoutesListRowLayoutBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun setData(position: Int) {

//            AppUtils.setImageWithRoundCorner(list.get(position), b.gifImgView, 20, 500)
        }
    }

    interface RecyclerViewClickListener {
        fun onClick(view: View?, position: Int)
    }
}