package com.greetings.allwishes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greetings.allwishes.R
import com.greetings.allwishes.ui.util.OnItemClickListener_Quotes

class QuotesAdapter(
    var quote_list: List<String>,
    var context: Context,
    private val onitemclicklistenerQuotes: OnItemClickListener_Quotes
) : RecyclerView.Adapter<QuotesAdapter.MyViewModel>() {
    class MyViewModel(view: View, onitemclicklistenerQuotes: OnItemClickListener_Quotes) :
        RecyclerView.ViewHolder(view) {
        val quotes_textview: TextView = view.findViewById(R.id.qutes_id)

        init {
            view.setOnClickListener {
                onitemclicklistenerQuotes.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.quotes_row_item, parent, false)
        return MyViewModel(view, onitemclicklistenerQuotes)
    }

    override fun getItemCount(): Int {
        return quote_list.size
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        holder.quotes_textview.text = quote_list[position]
    }
}