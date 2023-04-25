package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.examp.allwishes.databinding.DailyWishesCategoryActivityBinding
import com.examp.allwishes.ui.viewmodel.DailyWishesViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DailyWishes_CategoryFrag : Fragment() {

    lateinit var b: DailyWishesCategoryActivityBinding
    private lateinit var mainViewModel: DailyWishesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = DailyWishesCategoryActivityBinding.inflate(inflater, container, false)
        setupViewModel()

        val arrayList = mainViewModel.getComModel()
        if (arrayList == null) {
            observEvents()
        }
        println("Sahadadaadada  " + arrayList)
//        mainViewModel.getComModel().observe(requireActivity()) { model ->
//            b.cateRecycler.adapter = BannerAdapter(requireActivity(),
//                model,
//                object : BannerAdapter.RecyclerViewClickListener {
//                    override fun onClick(view: View?, position: Int) {
//                        val b = Bundle()
//                        b.putInt("pos", position)
//                        b.putString("trending_cat", model?.dailyWishes?.get(position)?.name)
//                        AppUtils.changeFragment(requireActivity(), R.id.nav_theme_cat, b)
//                    }
//
//                })
//
//        }

        val view = b.root
        return view
    }

    private fun observEvents() {
        GlobalScope.launch {
            mainViewModel.arrayListLiveData?.collect { model ->
                println("admkasndjmsjdfsj " + model)
            }
        }
    }


    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(requireActivity())[DailyWishesViewModel::class.java]
    }


}