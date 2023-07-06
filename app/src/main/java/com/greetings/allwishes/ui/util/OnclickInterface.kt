package com.greetings.allwishes.ui.util

import android.view.View


data class PopularCategory(var po: Int? = null, var poname: String = "popular")


interface OnItemClickListener {
    fun onClick(position: Int)
}

interface QuotesItemClickListener {
    fun onClick(view: View)
}

interface GetViewItemClickListener {
    fun onClick(view: View)
}

interface OnItemClickListener_Gif {
    fun onClick(position: Int)
}


interface OnItemClickListener_Quotes {
    fun onClick(position: Int)
}

interface OnItemClickListener_Save_Share {

    fun save()
    fun share()

}

interface StickerOnItemClick {

    fun onClick(view: View, position: Int)
}