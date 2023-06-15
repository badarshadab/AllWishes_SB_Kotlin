package com.examp.allwishes.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.modlueinfotech.allwishesgif.ui.activites.gif.Cards
import com.modlueinfotech.allwishesgif.ui.activites.gif.Frames
import com.modlueinfotech.allwishesgif.ui.activites.gif.Gifs
import com.modlueinfotech.allwishesgif.ui.activites.gif.Quotes

class MyTAbAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val array: Array<String>,
    val catName: String
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return array.size
    }


    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return Gifs(catName)
            1 -> return Frames(catName)
            2 -> return Cards(catName)
            else -> return Quotes(catName)

        }
    }

}