package com.greetings.allwishes.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.greetings.allwishes.databinding.FragmentContentPreviewBinding
import com.greetings.allwishes.ui.adapter.ContentPreviewAdapter
import com.greetings.allwishes.ui.data.api.FirebaseHelper
import com.greetings.allwishes.ui.viewmodel.HomeViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.greetings.allwishes.util.AdUtils


class ContentPreviewFragment : Fragment() {
    private lateinit var b: FragmentContentPreviewBinding

    private var type: String = ""
    private var category: String = ""
    private var wishesName: String = ""
    var index: Int? = 0
    private lateinit var mainViewModel: HomeViewModel
    var toolText = ""


    lateinit var activity: Activity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(!wishesName.equals("null")){
            toolText = wishesName + " " +type
        }
        else{
            toolText = category + " " +type
        }

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = toolText
        super.onViewCreated(view, savedInstanceState)
    }

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
        wishesName = arguments?.getString("wishesName").toString()
        index = arguments?.getInt("position", 0)
//        println("arguments?.getInt(pos) " + index)
        setupViewModel()
        if(!wishesName.equals("null")){
            mainViewModel.loadImagesStorage(category + "/" + wishesName + "/" + type)
        }
        else{
            mainViewModel.loadImagesStorage(category + "/" +  type)
        }

        setupObservers()
        AdUtils.showNativeBanner(
            requireActivity(),
            b.nativeAdContainer
        )

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
        var list = resource.asReversed()
        val adapter = ContentPreviewAdapter(
            activity, list, type
        )
        b.vp.adapter = adapter
        b.vp.setCurrentItem(index!!, true)

    }

}