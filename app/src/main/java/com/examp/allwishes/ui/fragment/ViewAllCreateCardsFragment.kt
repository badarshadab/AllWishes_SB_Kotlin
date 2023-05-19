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
import com.examp.allwishes.databinding.MainFragmentLayoutNewBinding
import com.examp.allwishes.databinding.ViewallCreatecardsFragmentBinding
import com.examp.allwishes.ui.activity.Holidays_List
import com.examp.allwishes.ui.adapter.CreateCardsAdapter
import com.examp.allwishes.ui.adapter.ViewAllCardsAdapter
import com.examp.allwishes.ui.model.Root_HlNew
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.DailyWishesViewModel


class ViewAllCreateCardsFragment : Fragment(){
    private lateinit var b: ViewallCreatecardsFragmentBinding
    private val mainViewModel: DailyWishesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = ViewallCreatecardsFragmentBinding.inflate(inflater, container, false)

        b.createCardRv.layoutManager =
            GridLayoutManager(requireContext(), 3)
        mainViewModel.repositoryResponseLiveData.observe(requireActivity()) { model ->
            if (model != null) {
                createAdapter(model)
            }
        }
        return b.root
    }

    private fun createAdapter(model: Root_HlNew) {
        b.createCardRv.adapter = model?.let {
            ViewAllCardsAdapter(
                requireActivity(), it, object : ViewAllCardsAdapter.RecyclerViewClickListener {
                    override fun onClick(view: View?, position: Int, catName: String?) {
                        val b = Bundle()
                        b.putString("catName", catName)
                        AppUtils.changeFragment(requireActivity(), R.id.nav_create_cards_list, b)
                    }
                })
        }
    }



}
