package com.examp.allwishes.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.examp.allwishes.R
import com.examp.allwishes.databinding.QuoteslistdlayoutBinding
import com.examp.allwishes.ui.adapter.QuotesListHolidayAdapter
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.QuoteViewModel


class Quotes(var catName: String) : Fragment() {

    lateinit var binding: QuoteslistdlayoutBinding
    private lateinit var quotesViewModel: QuoteViewModel
    private var trending_cat: String = ""


    lateinit var activity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QuoteslistdlayoutBinding.inflate(inflater, container, false)
        setupViewModel()
        if (catName == null) {
            trending_cat = arguments?.getString("catName").toString()
            setupObservers(trending_cat + "/Quotes")
        }
        else{
            setupObservers(catName + "/Quotes")
        }


        return binding.root
    }

    private fun setupViewModel() {
        quotesViewModel =
            ViewModelProvider(this)[QuoteViewModel::class.java]
    }


    private fun setupObservers(categoryName: String) {
        quotesViewModel.getQuotes(categoryName)
        quotesViewModel.quotes.observe(requireActivity()) { list ->
            setAdapter(list)
        }
    }

    private fun setAdapter(list: List<String>) {
        var adapter = QuotesListHolidayAdapter(
            activity,
            list,
            object : QuotesListHolidayAdapter.RecyclerViewClickListener {
                override fun onClick(view: View?, position: Int) {
                    val b = Bundle()
                    b.putInt("pos", position)
                    b.putString("name", catName)
                    AppUtils.changeFragment(requireActivity(), R.id.nav_quotesPreview, b)
                }
            })
        binding.recyclerquote.adapter = adapter
    }


}


