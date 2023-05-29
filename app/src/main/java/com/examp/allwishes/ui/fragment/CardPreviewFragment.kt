package com.examp.allwishes.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.examp.allwishes.databinding.ActivityCardpreviewBinding
import com.examp.allwishes.databinding.FragmentContentPreviewBinding
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory


class CardPreviewFragment : Fragment() {
    private lateinit var b: ActivityCardpreviewBinding

    private var type: String = ""
    private var category: String = ""
    var src: Bitmap? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = ActivityCardpreviewBinding.inflate(inflater, container, false)
        type = arguments?.getString("type").toString()
        category = arguments?.getString("catName").toString()


        return b.root
    }

}