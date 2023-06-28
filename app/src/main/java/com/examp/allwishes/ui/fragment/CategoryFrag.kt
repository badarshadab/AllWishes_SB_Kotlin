package com.examp.allwishes.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.examp.allwishes.R
import com.examp.allwishes.databinding.FragmentCategoryBinding
import com.examp.allwishes.ui.activity.Holidays_List
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.examp.allwishes.ui.viewmodel.QuoteViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.adapter.ImageAdapter
import com.greetings.allwishes.modelfactory.MyViewModelFactory


class CategoryFrag(val pos: Int, val catName: String) : Fragment(),
    ImageAdapter.ImageClickListener {

    private lateinit var b: FragmentCategoryBinding
    private var type: String = ""
    private lateinit var mainViewModel: HomeViewModel

    private lateinit var quotesViewModel: QuoteViewModel
    private lateinit var list: List<StorageReference>

    lateinit var activity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
//        https://stackoverflow.com/questions/28672883/java-lang-illegalstateexception-fragment-not-attached-to-activity
//        Fragment ContentPreviewFragment{fb22d83} (743b8906-1fa7-4828-8024-cc60ff8aac63) not attached to an activity.
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentCategoryBinding.inflate(inflater, container, false)
        setupViewModel()
//        AdUtils.showBanner(requireActivity(), b.adContainer.nativeAdContainer)
        type = when (pos) {
            0 -> "Gifs"
            1 -> "Cards"
            2 -> "Quotes"
            3 -> "Frames"
            else -> { // Note the block
                ""
            }
        }
        b.progressBar.visibility = View.VISIBLE
        setupObservers(catName + "/" + type, pos)
//        println("position of the tab $pos")
        return b.root
    }

    override fun onResume() {
        super.onResume()
        if(this.list != null){

        }
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
        quotesViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]
    }

    private fun setupObservers(categoryName: String, cat: Int) {
        mainViewModel.loadImagesStorage(categoryName)

        if (cat != 2) {
            mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity()) { resource ->
                this.list = resource.asReversed()
                b.progressBar.visibility = View.GONE
                setCatCards(this.list)

            }

        } else {
            quotesViewModel.getQuotes(categoryName)
            quotesViewModel.quotes.observe(requireActivity()) { list ->
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
            if (pos != 2) {
                b.rv.layoutManager = GridLayoutManager(activity, 3)
            } else {
                b.rv.layoutManager = GridLayoutManager(activity, 1)
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
                Holidays_List.storageRef = storageReference
            }
            AppUtils.changeFragment(requireActivity(), R.id.nav_contentPreview, bun)
        }

    }


}