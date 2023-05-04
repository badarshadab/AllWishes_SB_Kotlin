package com.examp.allwishes.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.examp.allwishes.R
import com.examp.allwishes.databinding.MainFragmentLayoutBinding
import com.examp.allwishes.ui.activity.Holidays_List
import com.examp.allwishes.ui.util.AppUtils


class MainFragment : Fragment(), View.OnClickListener {
    private lateinit var b: MainFragmentLayoutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = MainFragmentLayoutBinding.inflate(inflater, container, false)
        b.startBtn.setOnClickListener(this)
        b.holidayBtn.setOnClickListener(this)
        b.sharepanel.privacy.setOnClickListener(this)
        b.sharepanel.rate.setOnClickListener(this)
        b.sharepanel.sharePkg.setOnClickListener(this)
        b.sharepanel.saveItems.setOnClickListener(this)
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
            b.sharepanel.privacy -> AppUtils.openUrl(
                requireContext(),
                resources.getString(R.string.pp_url)
            )
            b.sharepanel.rate -> AppUtils.rateUs(requireContext())
            b.sharepanel.sharePkg -> AppUtils.shareApp(requireContext())
            b.sharepanel.saveItems -> AppUtils.selectSavedTypeDialog(requireActivity())

        }
    }


}
