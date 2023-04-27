package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.examp.allwishes.databinding.FragmentContentListBinding

class ContentListFrag : Fragment() {
    lateinit var b: FragmentContentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentContentListBinding.inflate(inflater, container, false)
        return b.root
    }
}