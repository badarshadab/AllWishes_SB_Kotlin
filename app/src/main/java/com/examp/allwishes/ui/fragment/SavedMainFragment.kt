package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.examp.allwishes.databinding.SavedmainfragmentBinding
import com.examp.allwishes.ui.adapter.SavedViewpagerAdapter
import com.examp.allwishes.ui.util.AppUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SavedMainFragment : Fragment() {

    lateinit var _binding: SavedmainfragmentBinding
    var list = ArrayList<String>()

    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager2
    lateinit var toolbar: Toolbar
    val typeArray = arrayOf(
        "Gifs", "Frames", "Cards", "Quotes"
    )
    private var trending_cat: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = SavedMainFragment.inflate(inflater, container, false)
        _binding = SavedmainfragmentBinding.inflate(inflater, container, false)

        viewpager = _binding.vp
        tablayout = _binding.tl
        trending_cat = arguments?.getString("trending_cat").toString()


        toolbar = _binding.toolbar.toolbar
        AppUtils.setUpToolbar(
            requireActivity() as AppCompatActivity,
            toolbar,
            "Saved List",
            true
        )
//        println("in oncreate of Holday_list")
//        AdUtils.showNativeBanner(
//            Holidays_List.activity,
//            _binding.nativeAdContainer.nativeAdContainer
//        )
        viewpager.adapter = SavedViewpagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle,
            requireActivity(),
            typeArray,
            trending_cat
        )

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = typeArray[position]
        }.attach()

        return _binding.root
    }

}