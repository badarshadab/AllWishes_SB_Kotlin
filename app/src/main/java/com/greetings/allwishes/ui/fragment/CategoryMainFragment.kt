package com.greetings.allwishes.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.greetings.allwishes.R
import com.greetings.allwishes.databinding.CategorymainfragmentBinding
import com.greetings.allwishes.ui.adapter.MyTAbAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.greetings.allgreetingwishes.adapter.HostTabPagerAdapter
import com.greetings.allwishes.util.AdUtils


class CategoryMainFragment : Fragment() {

    lateinit var _binding: CategorymainfragmentBinding
    var list = ArrayList<String>()

    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager2

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

        val list = listOf(
            Gifs(trending_cat),
            Cards(trending_cat),
            Quotes(trending_cat),
            Frames(trending_cat)
        )

        viewpager.adapter = HostTabPagerAdapter(list, this)

        TabLayoutMediator(tablayout, viewpager) { tab, position ->

            when (position) {
                0 -> {
                    tab?.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_gif_tab
                    )
                }

                1 -> {
                    tab?.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_cards_tab
                    )
                }

                2 -> {
                    tab?.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_quotes_tab
                    )
                }

                else -> {
                    tab?.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_frame_tab
                    )
                }
            }

            tab.text = titles[position]
        }.attach()
        return _binding.root
    }

}

