package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.examp.allwishes.databinding.FragmentSavedBinding
import com.examp.allwishes.ui.adapter.DownloadedAdapter
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.viewmodel.QuoteViewModel
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.greetings.allwishes.util.AdUtils
import com.sm.allwishes.greetings.util.ShareUtils


class SavedFragment(val pos: Int) : Fragment() {
    private lateinit var b: FragmentSavedBinding
    var list: ArrayList<String>? = null
    private lateinit var quotesViewModel: QuoteViewModel
    lateinit var favList: ArrayList<com.examp.allwishes.ui.model.FavoriteData>
    lateinit var downloadedAdapter: DownloadedAdapter
    private var type: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentSavedBinding.inflate(inflater, container, false)
        setupViewModel()
        favList = ArrayList()
        type = when (pos) {
            0 -> "Gifs"
            1 -> "Frames"
            2 -> "Cards"
            3 -> "Quotes"
            else -> { // Note the block
                ""
            }

        }

        list = ShareUtils.getCollection(requireActivity(), "/" + type)

        if (list!!.size > 0) {
            b.nosaved.visibility = View.GONE
        } else {
            b.nosaved.visibility = View.VISIBLE
        }
        b.rv.setLayoutManager(
            LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        downloadedAdapter = DownloadedAdapter(list!!)
        b.rv.adapter = downloadedAdapter
        if (list!!.size > 0) {
            AdUtils.showNativeBanner(
                requireActivity(),
                b.adContainer.nativeAdContainer
            )
        }

        return b.root
    }


    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        quotesViewModel = ViewModelProvider(this, myViewModelFactory)[QuoteViewModel::class.java]
    }
}