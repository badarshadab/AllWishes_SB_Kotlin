package com.greetings.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.greetings.allwishes.R
import com.greetings.allwishes.databinding.ViewallCreatecardsFragmentBinding
import com.greetings.allwishes.ui.adapter.ViewAllCardsAdapter
import com.greetings.allwishes.ui.model.Root_HlNew
import com.greetings.allwishes.ui.util.AppUtils
import com.greetings.allwishes.ui.viewmodel.DailyWishesViewModel
import com.greetings.allwishes.util.AdUtils


class ViewAllCreateCardsFragment : Fragment() {
    private lateinit var b: ViewallCreatecardsFragmentBinding
    private val mainViewModel: DailyWishesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = ViewallCreatecardsFragmentBinding.inflate(inflater, container, false)
        AdUtils.showNativeBanner(requireActivity(), b.nativeAdContainer.nativeAdContainer)
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
                        AppUtils.changeFragment(requireActivity(), R.id.nav_set_cards, b)
                    }
                })
        }
    }


}
