package com.examp.allwishes.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.examp.allwishes.R
import com.examp.allwishes.databinding.MainFragmentLayoutBinding
import com.examp.allwishes.databinding.MainFragmentLayoutNewBinding
import com.examp.allwishes.ui.activity.Holidays_List
import com.examp.allwishes.ui.adapter.BannerAdapter
import com.examp.allwishes.ui.adapter.CreateCardsAdapter
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.DailyWishesViewModel


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
//        b.sharepanel.privacy.setOnClickListener(this)
//        b.sharepanel.rate.setOnClickListener(this)
//        b.sharepanel.sharePkg.setOnClickListener(this)
//        b.sharepanel.saveItems.setOnClickListener(this)
        b.createCardRv.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        mainViewModel.getComModel().observe(requireActivity()){model ->
            b.createCardRv.adapter = model?.let {
                CreateCardsAdapter(
                    requireActivity(), it, object : CreateCardsAdapter.RecyclerViewClickListener {
                        override fun onClick(view: View?, position: Int, catName: String?) {
                            val b = Bundle()
                            b.putString("catName", catName)
                            AppUtils.changeFragment(requireActivity(), R.id.nav_daily_type, b)
                        }

                    })
            }
        }
        return b.root
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
                val inten1 = Intent(requireContext(), Holidays_List::class.java)
                startActivity(inten1)
            }
//            b.sharepanel.privacy -> AppUtils.openUrl(
//                requireContext(),
//                resources.getString(R.string.pp_url)
//            )
//            b.sharepanel.rate -> AppUtils.rateUs(requireContext())
//            b.sharepanel.sharePkg -> AppUtils.shareApp(requireContext())
//            b.sharepanel.saveItems -> AppUtils.selectSavedTypeDialog(requireActivity())

        }
    }


}
