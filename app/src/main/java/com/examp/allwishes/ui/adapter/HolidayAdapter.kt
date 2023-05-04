package com.examp.allwishes.ui.adapter

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.examp.allwishes.R
import com.examp.allwishes.databinding.HolidayRowLayoutBinding
import com.examp.allwishes.ui.activity.Holidays_List
import com.examp.allwishes.ui.model.Event
import com.examp.allwishes.ui.util.AppUtils
import com.greetings.allwishes.util.AdUtils

class HolidayAdapter(public val activity: Activity, val msgList: ArrayList<Event>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var layoutInflater: LayoutInflater? = null

    class HolidayViewHolder(val b: HolidayRowLayoutBinding) : RecyclerView.ViewHolder(b.root) {

        fun ViewHolder(itemView: View) {


            val typeface = Typeface.createFromAsset(Holidays_List.activity.getAssets(), "fonts/ALEO-REGULAR.OTF")
            b.quotesText.setTypeface(typeface)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val b = HolidayRowLayoutBinding.inflate(layoutInflater!!, parent, false)
        return HolidayViewHolder(b)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val holder = holder as HolidayViewHolder
        val festival_name: String = msgList!![position].name.toString()

        holder.b.quotesText.setText(festival_name)
        AppUtils.setImageWithRoundCorner(msgList!![position].icon.toString(), holder.b.iv, 40, 200)
//        AppUtils.setScaleAnimation(holder.itemView);
        //        AppUtils.setScaleAnimation(holder.itemView);
        holder.itemView.setOnClickListener(View.OnClickListener { v: View? ->
            println("clicked here $position")
            val b = Bundle()
            b.putInt("pos", position)
            b.putString("trending_cat", festival_name)
            AppUtils.changeFragment(activity, R.id.nav_cat_main, b)
//            AdUtils.changeFragment(activity, R.id.nav_cat_main, b)
        })
    }


    override fun getItemCount(): Int {
        return msgList!!.size
    }


}