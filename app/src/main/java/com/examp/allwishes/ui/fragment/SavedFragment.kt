package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.examp.allwishes.databinding.FragmentSavedBinding
import com.examp.allwishes.ui.adapter.DownloadedAdapter
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.greetings.allwishes.util.AdUtils
import com.sm.allwishes.greetings.util.ShareUtils


class SavedFragment(val pos: Int, val clickedType: String) : Fragment() {
    private lateinit var b: FragmentSavedBinding
    var list: ArrayList<String>? = null
    lateinit var favList: ArrayList<com.examp.allwishes.ui.model.FavoriteData>
    lateinit var downloadedAdapter: DownloadedAdapter
    private var type: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentSavedBinding.inflate(inflater, container, false)
//        setupViewModel()
        favList = ArrayList()


        if (clickedType.equals("daily")) {
            type = when (pos) {
                0 -> "Gif"
                1 -> "Card"
                2 -> "Quote"
                else -> { // Note the block
                    ""
                }
            }
        } else {
            type = when (pos) {
                0 -> "Gifs"
                1 -> "Frames"
                2 -> "Cards"
                3 -> "Quotes"
                else -> { // Note the block
                    ""
                }
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
        downloadedAdapter = DownloadedAdapter(list!!, requireActivity())
        list?.reverse()
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
//        quotesViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]
    }
}