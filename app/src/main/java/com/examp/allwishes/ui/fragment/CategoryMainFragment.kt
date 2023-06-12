package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.examp.allwishes.R
import com.examp.allwishes.databinding.CategorymainfragmentBinding
import com.examp.allwishes.ui.adapter.CategoryViewpagerAdapter
import com.examp.allwishes.ui.model.EventByMonth
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.greetings.allwishes.util.AdUtils


class CategoryMainFragment : Fragment() {

    lateinit var _binding: CategorymainfragmentBinding
    var list = ArrayList<String>()

    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager2
    private var arrayList: ArrayList<EventByMonth>? = null
    val typeArray = arrayOf(
        "Gifs", "Frames", "Cards", "Quotes"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = trending_cat
        super.onViewCreated(view, savedInstanceState)
    }

    private var trending_cat: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CategorymainfragmentBinding.inflate(inflater, container, false)

        viewpager = _binding.vp
        tablayout = _binding.tl
        trending_cat = arguments?.getString("trending_cat").toString()
//        tablayout.getTabAt(0)?.setIcon(R.drawable.colorbtn)
//        tablayout.getTabAt(1)?.setIcon(R.drawable.colorbtn)
//        tablayout.getTabAt(2)?.setIcon(R.drawable.colorbtn)
//        tablayout.getTabAt(3)?.setIcon(R.drawable.colorbtn)
        AdUtils.showNativeBanner(
            requireActivity(),
            _binding.nativeAdContainer
        )
        viewpager.adapter = CategoryViewpagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle,
            requireActivity(),
            typeArray,
            trending_cat
        )
//        tablayout.setupWithViewPager(viewpager)

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = typeArray[position]
        }.attach()

        return _binding.root
    }

}