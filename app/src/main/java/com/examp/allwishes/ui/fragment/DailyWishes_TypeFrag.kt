package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.examp.allwishes.R
import com.examp.allwishes.databinding.DailywishesTypeLayBinding
import com.examp.allwishes.ui.util.AppUtils


class DailyWishes_TypeFrag : Fragment(), View.OnClickListener {

    lateinit var b: DailywishesTypeLayBinding
    private var catName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        catName = arguments?.getString("catName").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = DailywishesTypeLayBinding.inflate(inflater, container, false)
        b.typeBtns.gifBtn.setOnClickListener(this)
        b.typeBtns.imgBtn.setOnClickListener(this)
        b.typeBtns.quotes.setOnClickListener(this)
        val view = b.root
        return view
    }

    override fun onClick(v: View?) {

        when (v) {
            b.typeBtns.gifBtn -> {
                move("Gif")
            }
            b.typeBtns.imgBtn ->
                move("Cards")
            b.typeBtns.quotes ->
                Toast.makeText(requireContext(), "clicked on Quotes Button", Toast.LENGTH_SHORT)
                    .show()
        }

    }
    fun move(type : String){
        val b = Bundle()
        b.putString("catName", catName)
        b.putString("type", type)
        AppUtils.changeFragment(requireActivity(), R.id.nav_content_list, b)
    }

}