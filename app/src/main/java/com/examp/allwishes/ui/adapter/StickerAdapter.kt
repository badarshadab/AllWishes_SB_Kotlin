package com.examp.allwishes.ui.adapter

//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.examp.allwishes.databinding.ContentPreviewRowBinding
//import com.examp.allwishes.ui.util.AppUtils
//import com.google.firebase.storage.StorageReference
//
//class StickerAdapter(
//    val list: List<StorageReference>,
//    RecyclerViewClickListener mListener
//) :
//    RecyclerView.Adapter<ContentPreviewAdapter.ContentPreviewHolder>() {
//
//
//    private var layoutInflater: LayoutInflater? = null
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ContentPreviewAdapter.ContentPreviewHolder {
//        if (layoutInflater == null) {
//            layoutInflater = LayoutInflater.from(parent.context)
//        }
//        val contentPreviewRowBinding =
//            ContentPreviewRowBinding.inflate(layoutInflater!!, parent, false)
//
//
//        return ContentPreviewHolder(contentPreviewRowBinding)
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    override fun onBindViewHolder(
//        holder: ContentPreviewAdapter.ContentPreviewHolder,
//        position: Int
//    ) {
//        val myViewHolder = (MyViewHolder) holder
//                AppUtils.setImage(myViewHolder.imageView, list.get(position))
//    }
//
//
//}