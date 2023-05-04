package com.examp.allwishes.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examp.allwishes.databinding.ContentPreviewRowBinding
import com.examp.allwishes.ui.activity.Holidays_List
import com.examp.allwishes.ui.util.AppUtils
import com.google.firebase.storage.StorageReference
import com.sm.allwishes.greetings.util.ShareUtils

class ContentPreviewAdapter(
    val activity: Activity,
    val list: List<StorageReference>,
    val type: String
) :
    RecyclerView.Adapter<ContentPreviewAdapter.ContentPreviewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    private var rv: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentPreviewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val contentPreviewRowBinding =
            ContentPreviewRowBinding.inflate(layoutInflater!!, parent, false)


        return ContentPreviewHolder(contentPreviewRowBinding)
    }

    override fun onBindViewHolder(holder: ContentPreviewHolder, position: Int) {
        holder.setData(position)
        println("Shadab.ViewHolder " + position)

        holder.b.actionLay.download.setOnClickListener({
            ShareUtils.saveItem(activity, list.get(position), type)
        })
        holder.b.actionLay.share.setOnClickListener({
            ShareUtils.shareGIF(
                activity, list.get(position)
            )
        })
        holder.b.actionLay.whatsapp.setOnClickListener({
            ShareUtils.shareGIFOnWhatsApp(
                activity, list.get(position)
            )
        })
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

    inner class ContentPreviewHolder(val b: ContentPreviewRowBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun setData(position: Int) {
            AppUtils.setImageWithRoundCorner(list.get(position), b.iv, 20, 500)
        }
    }
}