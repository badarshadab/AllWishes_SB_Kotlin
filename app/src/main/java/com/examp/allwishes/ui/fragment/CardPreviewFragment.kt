package com.examp.allwishes.ui.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import com.examp.allwishes.databinding.ActivityCardpreviewBinding
import com.examp.allwishes.ui.util.AppUtils
import com.greetings.allwishes.util.AdUtils
import com.sm.allwishes.greetings.util.ShareUtils
import java.io.FileNotFoundException


class CardPreviewFragment : Fragment() {
    private lateinit var b: ActivityCardpreviewBinding

    private var intdata: String = ""
    var src: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = ActivityCardpreviewBinding.inflate(inflater, container, false)
        intdata = arguments?.getString("bitimgkey").toString()
        if (intdata != null) {
            try {
                src = BitmapFactory.decodeStream(requireContext().openFileInput("ImageName"))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        AdUtils.showNativeBanner(requireActivity() , b.adsLayout)
        b.cardimgid.setImageBitmap(src)

        b.cardssaveBtn.setOnClickListener {
            val bmp: Bitmap? = b.cardimgid.drawToBitmap()
            val file = AppUtils.getFile(requireContext(), bmp)
            if (file != null) {
                ShareUtils.saveItem(requireActivity(), file, "Cards")
            }
        }
        b.cardsshareBtn.setOnClickListener {
            val bmp: Bitmap? = b.cardimgid.drawToBitmap()
            if (bmp != null) {
                AppUtils.shareBitmap(requireContext(), bmp)
            }
        }

        return b.root


//        b.cardsshareBtn.setOnClickListener {
//            Util.OnClickShare(binding.cardimgid, this)
//
//        }
    }

}