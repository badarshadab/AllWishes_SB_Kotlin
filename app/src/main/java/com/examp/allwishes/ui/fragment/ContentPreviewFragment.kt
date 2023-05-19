package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.examp.allwishes.databinding.FragmentContentPreviewBinding
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory


class ContentPreviewFragment : Fragment() {
    private lateinit var b: FragmentContentPreviewBinding

    private var type: String = ""
    private var category: String = ""
    private var list: List<StorageReference>? = null
    var saveType: String = ""
    var index: Int? = 0
    private lateinit var mainViewModel: HomeViewModel

    lateinit var listtoShare: List<StorageReference>
    lateinit var toolbar: Toolbar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentContentPreviewBinding.inflate(inflater, container, false)
        type = arguments?.getString("type").toString()
        category = arguments?.getString("catName").toString()
        index = arguments?.getInt("position", 0)
        println("arguments?.getInt(pos) " + index)
        setupViewModel()
        setupObservers(category)
//        AdUtils.showNativeBanner(
//            Holidays_List.activity,
//            b.nativeAdContainer
//        )

        toolbar = b.toolbar.toolbar

        AppUtils.setUpToolbar(
            requireActivity() as AppCompatActivity,
            toolbar,
            type + " Preview",
            true
        )

        return b.root
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
    }

    private fun setupObservers(categoryName: String) {

//        mainViewModel.loadImagesStorage(categoryName + "/" + type)
//            .observe(requireActivity(), Observer { it ->
//                it.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
////                            listtoShare = it
//                            println("setupObservers Status.SUCCESS" + resource.data)
//                            this.list = resource.data
//                            val adapter = list?.let {
//                                ContentPreviewAdapter(
//                                    requireActivity(),
//                                    it,
//                                    type
//                                )
//                            }
//                            println("the value of index ")
//                            b.vp.adapter = adapter
//                            index?.let {
//                                b.vp.postDelayed({
//                                    println("the value of index " + index)
//                                    b.vp.setCurrentItem(it, true)
//                                }, 100)
//                            }
//                        }
//                        Status.ERROR -> {
//                            println("setupObservers Status.ERROR" + resource.data)
//                        }
//                        Status.LOADING -> {
//                            println("setupObservers Status.LOADING" + resource.data)
//                        }
//                    }
//                }
//            })


    }
}