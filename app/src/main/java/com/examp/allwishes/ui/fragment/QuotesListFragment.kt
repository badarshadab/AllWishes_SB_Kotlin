package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.examp.allwishes.R
import com.examp.allwishes.databinding.FragmentContentListBinding
import com.examp.allwishes.ui.adapter.GifImageAdapter
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.util.Status
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory

class QuotesListFragment : Fragment() {
    private lateinit var b: FragmentContentListBinding
    private lateinit var mainViewModel: HomeViewModel
    private var list: List<StorageReference>? = null
    private var name: String = ""
    private var type: String = ""
    private var nameType: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentContentListBinding.inflate(inflater, container, false)

        name = arguments?.getString("catName").toString()
        type = arguments?.getString("type").toString()

        setupViewModel()
//        AdUtils.showBanner(requireActivity(), b.adContainer.nativeAdContainer)
        nameType = "DailyWishes/" + name + "/" + type
        setupObservers(nameType)

        if (type.equals("Frame")) {
            b.rv.layoutManager = GridLayoutManager(requireContext(), 2)

        } else {
            b.rv.layoutManager = GridLayoutManager(requireContext(), 3)
        }
        return b.root
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
    }


    private fun setupObservers(categoryName: String) {
        mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity()){resource->
            b.pb.visibility = View.GONE
//            println("setupObservers Status.SUCCESS" + resource.)
            this.list = resource.reversed()
            b.rv.adapter = resource.reversed().let { it1 ->
                GifImageAdapter(
                    requireActivity(),
                    it1, object : GifImageAdapter.RecyclerViewClickListener {
                        override fun onClick(
                            view: View?,
                            position: Int
                        ) {
                            val  b = Bundle()
                            b.putString("type" , type)
                            b.putString("catName" , "DailyWishes/"+name)
                            b.putInt("position" , position)
                            AppUtils.changeFragment(requireActivity() , R.id.nav_contentPreview , b)
                        }

                    }
                )
            }
        }
//        mainViewModel.loadImagesStorage(categoryName)
//            .observe(requireActivity(), Observer { it ->
//                it.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//
//                        }
//                        Status.ERROR -> {
//                            b.retry.root.visibility = View.VISIBLE
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