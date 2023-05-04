package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.examp.allwishes.R
import com.examp.allwishes.databinding.FragmentCategoryBinding
import com.examp.allwishes.ui.activity.Holidays_List
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.util.Status
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.examp.allwishes.ui.viewmodel.QuoteViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.adapter.ImageAdapter
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.greetings.allwishes.util.AdUtils


class CategoryFrag(val pos: Int, val catName: String) : Fragment(),
    ImageAdapter.ImageClickListener {
    private lateinit var b: FragmentCategoryBinding
    private var type: String = ""
    private lateinit var mainViewModel: HomeViewModel

    private lateinit var quotesViewModel: QuoteViewModel
    private var list: List<StorageReference>? = null
    lateinit var toolbar: Toolbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentCategoryBinding.inflate(inflater, container, false)
//        trending_cat = arguments?.getString("trending_cat").toString()
//        name = arguments?.getString("name").toString()
//        b.toolbar.toolbar.title = resources.getString(R.string.app_name)
        setupViewModel()
//        AdUtils.showBanner(requireActivity(), b.adContainer.nativeAdContainer)
        type = when (pos) {
            0 -> "Gifs"
            1 -> "Frames"
            2 -> "Cards"
            3 -> "Quotes"
            else -> { // Note the block
                ""
            }


        }
        b.progressBar.visibility = View.VISIBLE
        setupObservers(catName + "/" + type, pos)
        println("position of the tab $pos")
        return b.root
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
        quotesViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]
    }

    private fun setupObservers(categoryName: String, cat: Int) {
        if (cat != 3) {
            mainViewModel.loadImagesStorage(categoryName)
                .observe(requireActivity(), Observer { it ->
                    it.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                println("setupObservers Status.SUCCESS" + resource.data)
                                this.list = resource.data
                                setCatCards(resource.data)
                                b.progressBar.visibility = View.GONE
                            }
                            Status.ERROR -> {
                                println("setupObservers Status.ERROR" + resource.data)
                            }
                            Status.LOADING -> {
                                println("setupObservers Status.LOADING" + resource.data)
                            }
                        }
                    }
                })
        } else {
            quotesViewModel.getData(categoryName).observe(requireActivity()) { list ->
                setCatCards(list)
                b.progressBar.visibility = View.GONE
            }

        }
    }


    private fun setCatCards(list: List<Any>?) {
//        b.pb.visibility = View.GONE
        if (list.isNullOrEmpty()) {
//            b.retry.root.visibility = View.VISIBLE
//            b.retry.btn.setOnClickListener {
//                categoryName?.let { categoryName ->
//                    b.retry.root.visibility = View.GONE
//                    b.pb.visibility = View.VISIBLE
//                }
//            }
            return
        } else {
            activity?.let {
            }
            if (pos != 3) {
                b.rv.layoutManager = GridLayoutManager(requireContext(), 3)
            } else {
                b.rv.layoutManager = GridLayoutManager(requireContext(), 1)
            }
            b.rv.getCallerID("contentlist")
            if (type.isEmpty()) {
                type = "Gifs"
            }
            println("b.rv.setData(list) " + list)
            b.rv.getType(type)
            b.rv.setData(list)
            b.rv.setItemClickListener(this)
        }
    }

    override fun onCardClick(view: View, storageReference: Any, index: Int) {
        println("card has been clicked")

        if (type.equals("Frames")) {
            val b = Bundle()
            FrameEditFragment.any = storageReference as StorageReference
            AppUtils.changeFragment(requireActivity(), R.id.nav_frameEdit, b)

        } else if (type.equals("Quotes")) {
            val b = Bundle()
            b.putInt("pos", index)
            b.putString("name", catName)
            println("ist?.get(index)?.name " + list)
            AppUtils.changeFragment(requireActivity(), R.id.nav_quotesPreview, b)
        } else {
            val bun = Bundle()
            bun.putString("type", type)
            bun.putString("catName", catName)
            bun.putInt("position", index)
            if (storageReference is StorageReference) {
                Holidays_List.storageRef = storageReference as StorageReference
            }
            AppUtils.changeFragment(requireActivity(), R.id.nav_contentPreview, bun)
//            AdUtils.changeFragment(requireActivity(), R.id.nav_contentPreview, bun)
        }

    }

//    override fun onCardClick(view: View, obj: Any, index: Int) {
//        val bun = Bundle()
//
//        if (obj is StorageReference) {
//            DrawerActivity.storageRef = obj as StorageReference
//        } else {
//
//        }
//        AdUtils.changeFragment(requireActivity(), R.id.nav_contentPreview, bun)
//    }


}