package com.greetings.allwishes.ui.fragment

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.greetings.allwishes.R
import com.greetings.allwishes.databinding.FragmentQuotesPreviewBinding
import com.greetings.allwishes.ui.adapter.QuotesPreviewAdapter
import com.greetings.allwishes.ui.util.AppUtils
import com.greetings.allwishes.ui.viewmodel.QuoteViewModel
import com.greetings.allwishes.util.AdUtils
import com.sm.allwishes.greetings.util.ShareUtils

class QuotesPreviewFragment : Fragment(), View.OnClickListener {

    private lateinit var b: FragmentQuotesPreviewBinding
    private var index: Int? = null
    private lateinit var name: String
    lateinit var listA: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        name = arguments?.getString("name").toString()
        index = arguments?.getInt("pos", 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentQuotesPreviewBinding.inflate(inflater, container, false)
        val quoteViewModel =
            ViewModelProvider(requireActivity())[QuoteViewModel::class.java]
        AdUtils.showNativeBanner(requireActivity(), b.adContainer.nativeAdContainer)
        quoteViewModel.getQuotes(name + "/Quotes")
        quoteViewModel.quotes.observe(requireActivity()) { list ->

            if (!list.isNullOrEmpty()) {
                b.copy.setOnClickListener(this)
                b.save.setOnClickListener(this)
                b.shareImg.setOnClickListener(this)
                b.shareText.setOnClickListener(this)
            }

            b.shimmerLay.startShimmer()


            listA = list as ArrayList<String>
            if (listA.size > 0) {
                b.shimmerLay.stopShimmer()
                b.vp.visibility = View.VISIBLE
                b.shimmerLay.visibility = View.GONE
            }
            val adapter = list?.let { activity?.let { it1 -> QuotesPreviewAdapter(it1, it) } }
            b.vp.adapter = adapter
            index?.let {
                b.vp.postDelayed({ b.vp.setCurrentItem(it, true) }, 100)
            }
        }
        return b.root
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.shareImg -> {
                    val adapter = b.vp.adapter as QuotesPreviewAdapter
                    adapter.getTextView(b.vp.currentItem)?.let { it1 -> shareImage(it1) }
                }

                R.id.save -> {
                    val adapter = b.vp.adapter as QuotesPreviewAdapter
                    adapter.getTextView(b.vp.currentItem)?.let { it1 -> saveImage(it1) }
                }

                R.id.copy -> {
                    val adapter = b.vp.adapter as QuotesPreviewAdapter
                    copyText(adapter.getItem(b.vp.currentItem))
                }

                R.id.shareText -> {
                    val adapter = b.vp.adapter as QuotesPreviewAdapter
                    shareText(adapter.getItem(b.vp.currentItem))
                }

                else -> {

                }
            }
        }
    }

    private fun shareText(str: String) {
        AppUtils.shareString(requireContext(), str)
    }

    private fun copyText(str: String) {
        AppUtils.copyTextToClipBoard(requireContext(), str)
    }

    private fun shareImage(v: View) {
        val bm: Bitmap? = AppUtils.captureScreen(v)
        bm?.let {
            val uri: Uri? = AppUtils.getLocalBitmapUri(requireContext(), bm)
            AppUtils.shareBitmap(requireContext(), bm)
        }
    }

    private fun saveImage(v: View) {
        ShareUtils.saveQuotes(requireActivity(), v, "Quotes")
    }

}