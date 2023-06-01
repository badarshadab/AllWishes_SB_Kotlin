package com.examp.allwishes.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.examp.allwishes.databinding.FragmentContentPreviewBinding
import com.examp.allwishes.ui.adapter.ContentPreviewAdapter
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory


class ContentPreviewFragment : Fragment() {
    private lateinit var b: FragmentContentPreviewBinding

    private var type: String = ""
    private var category: String = ""
    var index: Int? = 0
    private lateinit var mainViewModel: HomeViewModel


    lateinit var activity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
//        https://stackoverflow.com/questions/28672883/java-lang-illegalstateexception-fragment-not-attached-to-activity
//        Fragment ContentPreviewFragment{fb22d83} (743b8906-1fa7-4828-8024-cc60ff8aac63) not attached to an activity.
    }

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
        mainViewModel.loadImagesStorage(category + "/" + type)
        setupObservers()
//        AdUtils.showNativeBanner(
//            requireActivity(),
//            b.nativeAdContainer
//        )

        return b.root
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(this, myViewModelFactory)[HomeViewModel::class.java]
    }

    private fun setupObservers() {

        mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity()) { resource ->
            setAdapter(resource)
        }


    }

    private fun setAdapter(resource: List<StorageReference>) {
        val adapter = ContentPreviewAdapter(
            activity, resource, type
        )
        b.vp.adapter = adapter
        b.vp.setCurrentItem(index!!, true)
    }

}