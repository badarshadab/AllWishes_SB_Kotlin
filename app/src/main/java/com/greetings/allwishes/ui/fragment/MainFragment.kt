package com.greetings.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.greetings.allwishes.R
import com.greetings.allwishes.databinding.MainFragmentLayoutNewBinding
import com.greetings.allwishes.ui.adapter.CreateCardsAdapter
import com.greetings.allwishes.ui.model.Root_HlNew
import com.greetings.allwishes.ui.util.AppUtils
import com.greetings.allwishes.ui.viewmodel.DailyWishesViewModel
import com.greetings.allwishes.util.AdUtils

class MainFragment : Fragment(), View.OnClickListener {
    private lateinit var b: MainFragmentLayoutNewBinding
    private val mainViewModel: DailyWishesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = MainFragmentLayoutNewBinding.inflate(inflater, container, false)
        b.startBtn.setOnClickListener(this)
        b.holidayBtn.setOnClickListener(this)
        b.viewAll.setOnClickListener(this)
        b.shimmerLay.startShimmer()

        AdUtils.showNative(requireActivity() , b.nativeAdContainer.nativeAdContainer)

        b.createCardRv.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        mainViewModel.repositoryResponseLiveData.observe(requireActivity()) { model ->
            if (model != null) {
                createAdapter(model)
            }
        }
        return b.root
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        (activity as AppCompatActivity?)!!.supportActionBar!!.title = resources.getString(R.string.app_name)
//        super.onViewCreated(view, savedInstanceState)
//    }

    override fun onResume() {
        AdUtils.showNative(requireActivity() , b.nativeAdContainer.nativeAdContainer)
        super.onResume()
    }

    private fun createAdapter(model: Root_HlNew) {

        if (model.createcards?.size!! > 0) {
            b.shimmerLay.stopShimmer()
            b.shimmerLay.visibility = View.GONE
            b.createCardRv.visibility = View.VISIBLE
        }
        val adapter = CreateCardsAdapter(
            requireActivity(),
            model,
            object : CreateCardsAdapter.RecyclerViewClickListener {
                override fun onClick(view: View?, position: Int, catName: String?) {
                    val b = Bundle()
                    b.putString("catName", catName)
                    AppUtils.changeFragmentWithPosition(
                        findNavController(),
                        R.id.action_nav_main_to_nav_set_cards,
                        requireActivity(),
                        b
                    )
                }


            })
        b.createCardRv.adapter = adapter

    }


    override fun onClick(v: View?) {
        when (v) {
            b.startBtn -> {
                val b = Bundle()
                AppUtils.changeFragmentWithPosition(
                    findNavController(),
                    R.id.action_nav_main_to_daily_catFrag,
                    requireActivity(),
                    b
                )
            }

            b.holidayBtn -> {
                val b = Bundle()
                AppUtils.changeFragment(requireActivity(), R.id.nav_home, b)
            }

            b.viewAll -> {
                val b = Bundle()
                AppUtils.changeFragment(requireActivity(), R.id.nav_view_create_cards, b)
            }
        }
    }


}
