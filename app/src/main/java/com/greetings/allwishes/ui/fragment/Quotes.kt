package com.greetings.allwishes.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.greetings.allwishes.R
import com.greetings.allwishes.databinding.QuoteslistholidayBinding
import com.greetings.allwishes.ui.adapter.QuotesListHolidayAdapter
import com.greetings.allwishes.ui.util.AppUtils
import com.greetings.allwishes.ui.viewmodel.QuoteViewModel


class Quotes(var catName: String) : Fragment() {

    lateinit var binding: QuoteslistholidayBinding
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
        binding = QuoteslistholidayBinding.inflate(inflater, container, false)
        setupViewModel()
        if (catName == null) {
            trending_cat = arguments?.getString("catName").toString()
            setupObservers(trending_cat + "/Quotes")
        } else {
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

            if (list != null) {
                binding.quoteprocessid.progresbarid.visibility = View.GONE
            }
            setAdapter(list)
        }
    }

    override fun onResume() {
        super.onResume()
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


