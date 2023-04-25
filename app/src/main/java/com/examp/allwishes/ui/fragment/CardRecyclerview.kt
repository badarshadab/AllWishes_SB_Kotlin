package com.examp.allwishes.ui.fragment

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greetings.allwishes.adapter.ImageAdapter

//import com.sm.allwishes.greetings.ui.adapter.ImageAdapter

class CardRecyclerview : RecyclerView {

    constructor(context: Context) : super(context) {
        init()
    }

    //
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = ImageAdapter()
    }

    //
    fun getMAdapter(): ImageAdapter {
        return adapter as ImageAdapter
    }

    fun getCallerID(callerID: String) {
        getMAdapter().getCaller(callerID)
    }

    fun getType(type: String) {
        getMAdapter().getType(type)
    }


    fun setData(list: List<Any>) {
        getMAdapter().setImageList(list)
    }


    fun setItemClickListener(listener: ImageAdapter.ImageClickListener) {
        getMAdapter().setClickListener(listener)
    }

}