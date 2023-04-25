package com.examp.allwishes.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.examp.allwishes.ui.fragment.HolidayFragment


class HolidayViewpagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val array: ArrayList<String>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return array.size
    }

    override fun createFragment(position: Int): Fragment {
        println("eventByMonth HolidayViewpagerAdapter $position")
        return HolidayFragment(position, array.get(position))
    }
}