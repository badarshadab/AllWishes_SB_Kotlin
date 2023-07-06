package com.greetings.allwishes.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greetings.allwishes.databinding.RecyclerGifImgRowBinding
import com.greetings.allwishes.ui.util.AppUtils
import com.google.firebase.storage.StorageReference

class GifImageAdapter(
    val activity: Activity,
    val list: List<StorageReference>,
    private var mListener: RecyclerViewClickListener? = null
) :
    RecyclerView.Adapter<GifImageAdapter.ContentPreviewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    private var rv: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentPreviewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val recyclerGifImgRowBinding =
            RecyclerGifImgRowBinding.inflate(layoutInflater!!, parent, false)


        return ContentPreviewHolder(recyclerGifImgRowBinding)
    }

    override fun onBindViewHolder(holder: ContentPreviewHolder, position: Int) {
        holder.setData(position)
        holder.b.gifImgView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                mListener?.onClick(v , position )
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


    inner class ContentPreviewHolder(val b: RecyclerGifImgRowBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun setData(position: Int) {
            AppUtils.setImageWithRoundCorner(list.get(position), b.gifImgView, 20, 500)
        }
    }
    interface RecyclerViewClickListener {
        fun onClick(view: View?, position: Int)
    }
}