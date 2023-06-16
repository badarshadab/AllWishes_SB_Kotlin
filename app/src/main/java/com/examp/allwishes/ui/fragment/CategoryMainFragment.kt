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
import com.examp.allwishes.ui.adapter.MyTAbAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.greetings.allgreetingwishes.adapter.HostTabPagerAdapter
import com.greetings.allwishes.util.AdUtils


class CategoryMainFragment : Fragment() {

    lateinit var _binding: CategorymainfragmentBinding
    var list = ArrayList<String>()

    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager2

    val tabIcons = intArrayOf(
        R.drawable.ic_gif_unselected,
        R.drawable.ic_cards_unselected,
        R.drawable.ic_quotes_unselected,
        R.drawable.ic_frame_unselected
    )

    val typeArray = arrayOf(
        "Gifs", "Frames", "Cards", "Quotes"
    )

    private var myTAbAdapter: MyTAbAdapter? = null

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

        AdUtils.showNativeBanner(
            requireActivity(),
            _binding.nativeAdContainer
        )

        val titles = listOf("Gifs", "Cards", "Quotes", "Frames")
//        val icon = listOf(
//            R.drawable.ic_gif_unselected,
//            R.drawable.ic_cards_unselected,
//            R.drawable.ic_quotes_unselected,
//            R.drawable.ic_frame_unselected
//        )

        val list = listOf(
            Gifs(trending_cat),
            Cards(trending_cat),
            Quotes(trending_cat),
            Frames(trending_cat)
        )

        viewpager.adapter = HostTabPagerAdapter(list, this)
//        viewpager.adapter = MyTAbAdapter(
//            requireActivity().supportFragmentManager,
//            lifecycle,
//            typeArray,
//            trending_cat
//        )

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tablayout.getTabAt(position)?.setIcon(tabIcons[position])
//            when (position) {
//
//                0 -> {
//                    tab.icon =
//                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_gif_unselected)
//                }
//
//                1 -> {
//                    tab.icon =
//                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_cards_unselected)
//                }
//
//                2 -> {
//                    tab.icon =
//                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_quotes_unselected)
//                }
//
//                3 -> {
//                    tab.icon =
//                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_frame_unselected)
//                }
//            }
            tab.text = titles[position]
//            AppUtils.createTabIcons(requireContext(), tablayout)

//            tab.setIcon(icon[position])
        }.attach()
//        setUpTabIcons()
        return _binding.root
    }

    fun setUpTabIcons() {
        tablayout.getTabAt(0)?.setIcon(tabIcons[0])
        tablayout.getTabAt(1)?.setIcon(tabIcons[1])
        tablayout.getTabAt(2)?.setIcon(tabIcons[2])
        tablayout.getTabAt(2)?.setIcon(tabIcons[3])
    }
}

