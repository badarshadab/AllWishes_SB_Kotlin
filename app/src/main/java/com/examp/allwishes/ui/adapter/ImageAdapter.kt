package com.greetings.allwishes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examp.allwishes.databinding.ImageRowBinding
import com.examp.allwishes.ui.util.AppUtils
import com.google.firebase.storage.StorageReference

class ImageAdapter() : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    var layoutInflater: LayoutInflater? = null

    val list = ArrayList<Any>()
    var listener: ImageClickListener? = null
    lateinit var mCaller: String
    lateinit var mType: String
    lateinit var saveType: String


    class ImageHolder(val b: ImageRowBinding) : RecyclerView.ViewHolder(b.root) {

        fun setImage(storageReference: StorageReference) {
            AppUtils.setImageWithRoundCorner(storageReference, b.iv, 20, 1000)
//            AppUtils.setImage(b.iv,storageReference)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val b = ImageRowBinding.inflate(layoutInflater!!, parent, false)
        return ImageHolder(b)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        println("mCaller.equals " + mCaller)
        if (mCaller.equals("randomhome") || mType.equals("Frame")) {
//            holder.b.actionLay.whatsapp.visibility = View.GONE
//            holder.b.actionLay.download.visibility = View.GONE
//            holder.b.actionLay.share.visibility = View.GONE
        }

        if (list[position] is StorageReference) {

            println("list[position]   " + list[position])
//            imgUrl = list[position] as StorageReference
            holder.setImage(list[position] as StorageReference)
            holder.b.tv.visibility = View.GONE
            holder.b.iv.visibility = View.VISIBLE
//            holder.b.actionLay.whatsapp.setText("Whatsapp")
//            holder.b.actionLay.whatsapp.setCompoundDrawablesWithIntrinsicBounds(
//                0,
//                R.drawable.ic_whatsapp,
//                0,
//                0
//            )
        } else {
            holder.b.iv.visibility = View.GONE
            holder.b.tv.visibility = View.VISIBLE
            val text = list[position].toString()
            holder.b.tv.setText(text)
//            holder.b.actionLay.whatsapp.setText("Copy Text")
//            holder.b.actionLay.whatsapp.setCompoundDrawablesWithIntrinsicBounds(
//                0,
//                R.drawable.ic_copy_text,
//                0,
//                0
//            )
        }

        holder.b.card.setOnClickListener {
            listener?.onCardClick(it, list[position], position)
            println("")
        }

//        holder.b.actionLay.download.setOnClickListener({
////            AppUtils.saveFavList(
////                "down_gifs",
////                DrawerActivity.activity,
////                list[position].toString()
////            )
//            println("ShareUtils.saveItem(DrawerActivity   " + mType)
//            if (mType.equals("GIF")) {
//                saveType = "gif"
//                ShareUtils.saveItem(DrawerActivity.activity, list.get(position), saveType)
//            } else if (mType.equals("Image") || mType.equals("Frame")) {
//                saveType = "jpg"
//                ShareUtils.saveItem(DrawerActivity.activity, list.get(position), saveType)
//            } else if (mType.equals("Quotes")) {
////                saveType = "png"
//                ShareUtils.saveQuotes(DrawerActivity.activity, holder.b.tv, "Quotes")
//            }
//
//        })
//        holder.b.actionLay.share.setOnClickListener({
//            if (mType.equals("Quotes")) {
//                ShareUtils.shareQuotes(DrawerActivity.activity,holder.b.tv)
//
//            }else{
//                ShareUtils.shareGIF(DrawerActivity.activity, list[position] as StorageReference)
//            }
//
//        })
//        holder.b.actionLay.whatsapp.setOnClickListener({
//            if (mType.equals("Quotes")) {
//                AppUtils.getInstance().copyTextToClipBoard(DrawerActivity.activity, list[position].toString())
//            } else {
//                ShareUtils.shareGIFOnWhatsApp(
//                    DrawerActivity.activity,
//                    list[position] as StorageReference
//                )
//            }
//
//        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setImageList(list: List<Any>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun getCaller(caller: String) {
        mCaller = caller
    }

    fun getType(type: String) {
        mType = type
    }


    fun setClickListener(listener: ImageClickListener) {
        this.listener = listener
    }

    interface ImageClickListener {
        fun onCardClick(view: View, storageReference: Any, index: Int)
    }

}