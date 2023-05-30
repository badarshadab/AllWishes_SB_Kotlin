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
import com.examp.allwishes.ui.adapter.ContentPreviewAdapter
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
        mainViewModel.loadImagesStorage(category + "/" + type)
        setupObservers()
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

    private fun setupObservers() {

        mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity()) { resource ->
            val adapter = ContentPreviewAdapter(
                requireActivity(), resource, type
            )
            b.vp.adapter = adapter
            b.vp.setCurrentItem(index!!, true)
        }


    }
//    private fun createAdapter(resource: List<StorageReference>) {
//
//
//        val adapter = ContentPreviewAdapter(
//            requireActivity(), resource, object : OnItemClickListener {
//                override fun onClick(position: Int) {
//                    val b = Bundle()
//                    AppUtils.changeFragmentWithPosition(
//                        findNavController(),
//                        R.id.action_nav_create_cards_list_to_nav_set_cards,
//                        requireActivity(),
//                        b
//                    )
//                }
//            })
//        b.addbgrecycleid.adapter = adapter
//        adapter.notifyDataSetChanged()
//    }
}