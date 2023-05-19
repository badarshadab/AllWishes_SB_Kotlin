package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.examp.allwishes.R
import com.examp.allwishes.databinding.FragmentContentListBinding
import com.examp.allwishes.ui.adapter.GifImageAdapter
import com.examp.allwishes.ui.adapter.QuotesListAdapter
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.examp.allwishes.ui.viewmodel.QuoteViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory

class ContentListFragment : Fragment() {
    private lateinit var b: FragmentContentListBinding
    private lateinit var mainViewModel: HomeViewModel
    private lateinit var quotesViewModel: QuoteViewModel
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

        if (type.equals("Quotes")) {
            b.rv.layoutManager = GridLayoutManager(requireContext(), 1)

        } else {
            b.rv.layoutManager = GridLayoutManager(requireContext(), 3)
        }
        return b.root
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
        quotesViewModel =
            ViewModelProvider(this)[QuoteViewModel::class.java]
    }


    private fun setupObservers(categoryName: String) {
        mainViewModel.loadImagesStorage(categoryName)
        if (type.equals("Quotes")) {
            quotesViewModel.getData(categoryName).observe(requireActivity()) { list ->
                if (list != null) {
                    b.pb.visibility = View.GONE
                }
                b.rv.adapter = QuotesListAdapter(
                    requireActivity(),
                    list,
                    object : QuotesListAdapter.RecyclerViewClickListener {
                        override fun onClick(view: View?, position: Int) {
                            val b = Bundle()
                            b.putString("name", "DailyWishes/" + name)
                            b.putInt("pos", position)
                            AppUtils.changeFragment(
                                requireActivity(),
                                R.id.nav_quotesPreview,
                                b
                            )
                        }
                    })
            }
        } else {
            mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity())
            { resource ->
                b.pb.visibility = View.GONE
                this.list = resource
                b.rv.adapter = this.list?.let { it1 ->
                    GifImageAdapter(
                        requireActivity(),
                        it1, object : GifImageAdapter.RecyclerViewClickListener {
                            override fun onClick(
                                view: View?,
                                position: Int
                            ) {
                                val b = Bundle()
                                b.putString("type", type)
                                b.putString("catName", "DailyWishes/" + name)
                                b.putInt("position", position)
                                AppUtils.changeFragment(
                                    requireActivity(),
                                    R.id.nav_contentPreview,
                                    b
                                )
                            }

                        }
                    )
                }
            }
//            mainViewModel.loadImagesStorage(categoryName)
//                .observe(requireActivity(), Observer { it ->
//                    it.let { resource ->
//                        when (resource.status) {
//                            Status.SUCCESS -> {
//                                b.pb.visibility = View.GONE
//                                this.list = resource.data
//                                b.rv.adapter = resource.data?.let { it1 ->
//                                    GifImageAdapter(
//                                        requireActivity(),
//                                        it1, object : GifImageAdapter.RecyclerViewClickListener {
//                                            override fun onClick(
//                                                view: View?,
//                                                position: Int
//                                            ) {
//                                                val b = Bundle()
//                                                b.putString("type", type)
//                                                b.putString("catName", "DailyWishes/" + name)
//                                                b.putInt("position", position)
//                                                AppUtils.changeFragment(
//                                                    requireActivity(),
//                                                    R.id.nav_contentPreview,
//                                                    b
//                                                )
//                                            }
//
//                                        }
//                                    )
//                                }
//                            }
//                            Status.ERROR -> {
//                                b.retry.root.visibility = View.VISIBLE
//                                println("setupObservers Status.ERROR" + resource.data)
//                            }
//                            Status.LOADING -> {
//                                println("setupObservers Status.LOADING" + resource.data)
//                            }
//                        }
//                    }
//                })
        }


    }

}