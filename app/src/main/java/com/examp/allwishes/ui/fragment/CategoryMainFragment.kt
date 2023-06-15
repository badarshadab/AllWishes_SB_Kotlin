package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.examp.allwishes.R
import com.examp.allwishes.databinding.CategorymainfragmentBinding
import com.examp.allwishes.ui.adapter.MyTAbAdapter
import com.examp.allwishes.ui.util.AppUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.greetings.allwishes.util.AdUtils


class CategoryMainFragment : Fragment() {

    lateinit var _binding: CategorymainfragmentBinding
    var list = ArrayList<String>()

    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager2

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
        myTAbAdapter = MyTAbAdapter(
                requireActivity().supportFragmentManager,
                lifecycle,
                typeArray,
                trending_cat
        )
        viewpager.adapter = myTAbAdapter
        tablayout.apply {
            TabLayoutMediator(tablayout, viewpager) { tab, position ->
                tab.text = typeArray[position]
            }.attach()

            AppUtils.createTabIcons(requireContext(), tablayout)
        }
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab?.position) {
                    0 -> {

                        tablayout.getTabAt(0)?.setIcon(R.drawable.ic_gif_tab)
                        Toast.makeText(requireContext(), "clicked on Tab 1" , Toast.LENGTH_SHORT).show()
//                        AppUtils.changeFragment(requireActivity() , R.id.)
                    }

                    1 -> {
                        tablayout.getTabAt(1)?.setIcon(R.drawable.ic_cards_tab)
                        Toast.makeText(requireContext(), "clicked on Tab 2" , Toast.LENGTH_SHORT).show()
                    }

                    2 -> {
                        tablayout.getTabAt(2)?.setIcon(R.drawable.ic_quotes_tab)
                        Toast.makeText(requireContext(), "clicked on Tab 3" , Toast.LENGTH_SHORT).show()
                    }

                    3 -> {
                        tablayout.getTabAt(3)?.setIcon(R.drawable.ic_frame_tab)
                        Toast.makeText(requireContext(), "clicked on Tab 4" , Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        tablayout.getTabAt(0)?.setIcon(R.drawable.ic_gif_unselected)
                    }

                    1 -> {
                        tablayout.getTabAt(1)?.setIcon(R.drawable.ic_cards_unselected)
                    }

                    2 -> {
                        tablayout.getTabAt(2)?.setIcon(R.drawable.ic_quotes_unselected)
                    }

                    3 -> {
                        tablayout.getTabAt(3)?.setIcon(R.drawable.ic_frame_unselected)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })

        return _binding.root
    }

}