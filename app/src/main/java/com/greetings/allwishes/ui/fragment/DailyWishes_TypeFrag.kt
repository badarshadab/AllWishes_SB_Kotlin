package com.greetings.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.greetings.allwishes.R
import com.greetings.allwishes.databinding.DailywishesTypeLayBinding
import com.greetings.allwishes.ui.util.AppUtils
import com.greetings.allwishes.util.AdUtils


class DailyWishes_TypeFrag : Fragment(), View.OnClickListener {

    lateinit var b: DailywishesTypeLayBinding
    private var catName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        catName = arguments?.getString("catName").toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = catName
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = DailywishesTypeLayBinding.inflate(inflater, container, false)
        b.typeBtns.gifBtn.setOnClickListener(this)
        b.typeBtns.imgBtn.setOnClickListener(this)
        b.typeBtns.quotes.setOnClickListener(this)
        AdUtils.showNative(requireActivity() , b.adsLayout)
        val view = b.root
        return view
    }



    override fun onClick(v: View?) {

        when (v) {
            b.typeBtns.gifBtn -> {
                move("Gifs")
            }

            b.typeBtns.imgBtn ->
                move("Cards")

            b.typeBtns.quotes ->
                move("Quotes")
        }

    }

    fun move(type: String) {
        val b = Bundle()
        b.putString("catName", catName)
        b.putString("type", type)
        AppUtils.changeFragment(requireActivity(), R.id.nav_content_list, b)
    }

}