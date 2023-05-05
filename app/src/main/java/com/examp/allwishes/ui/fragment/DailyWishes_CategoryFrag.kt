package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.examp.allwishes.R
import com.examp.allwishes.databinding.DailyWishesCategoryActivityBinding
import com.examp.allwishes.ui.adapter.BannerAdapter
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.DailyWishesViewModel


class DailyWishes_CategoryFrag : Fragment() {

    lateinit var b: DailyWishesCategoryActivityBinding
    private val mainViewModel: DailyWishesViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = DailyWishesCategoryActivityBinding.inflate(inflater, container, false)

        b.cateRecycler.layoutManager =
            GridLayoutManager(requireContext(), 1)

        mainViewModel.getComModel().observe(requireActivity()) { model ->
            b.cateRecycler.adapter = BannerAdapter(
                requireActivity(), model, object : BannerAdapter.RecyclerViewClickListener {
                    override fun onClick(view: View?, position: Int, catName: String) {
                        val b = Bundle()
                        b.putString("catName", catName)
                        AppUtils.changeFragment(requireActivity(), R.id.nav_daily_type, b)
                    }
                })
        }

        val view = b.root
        return view
    }

}