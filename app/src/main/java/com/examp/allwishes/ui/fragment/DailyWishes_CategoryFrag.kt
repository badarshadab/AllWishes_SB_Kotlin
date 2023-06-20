package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.examp.allwishes.R
import com.examp.allwishes.databinding.DailyWishesCategoryActivityBinding
import com.examp.allwishes.ui.adapter.BannerAdapter
import com.examp.allwishes.ui.model.DailyWishe
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.DailyWishesViewModel
import com.greetings.allwishes.util.AdUtils


class DailyWishes_CategoryFrag : Fragment() {

    lateinit var b: DailyWishesCategoryActivityBinding
    private val mainViewModel: DailyWishesViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Daily Wishes"
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = DailyWishesCategoryActivityBinding.inflate(inflater, container, false)

        AdUtils.showNativeBanner(requireActivity() ,b.nativeAdContainer.nativeAdContainer)
        b.cateRecycler.layoutManager =
            GridLayoutManager(requireContext(), 1)

        mainViewModel.repositoryResponseLiveData.observe(requireActivity()) { model ->
            model?.dailyWishes?.let { createAdapter(it) }
        }
        return b.root
    }

    private fun createAdapter(model: ArrayList<DailyWishe>) {
        b.cateRecycler.adapter = BannerAdapter(
            requireActivity(), model, object : BannerAdapter.RecyclerViewClickListener {
                override fun onClick(view: View?, position: Int) {
                    val b = Bundle()
                    b.putString("catName", model.get(position).name)
                    AppUtils.changeFragment(requireActivity(), R.id.nav_daily_type, b)
                }
            })
    }
}