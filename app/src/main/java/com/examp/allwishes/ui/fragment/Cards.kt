package com.examp.allwishes.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.examp.allwishes.R
import com.examp.allwishes.databinding.FragmentGifBinding
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.util.OnItemClickListener_Gif
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.modlueinfotech.allwishesgif.adapters.recyclerview.CardAdapter


class Cards(var catName: String) : Fragment() {

    lateinit var binding: FragmentGifBinding
    private lateinit var mainViewModel: HomeViewModel
    private var trending_cat: String = ""


    private var list: List<StorageReference>? = null

    lateinit var activity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGifBinding.inflate(inflater, container, false)
        setupViewModel()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (catName == null) {
            trending_cat = arguments?.getString("catName").toString()
            setupObservers(trending_cat + "/Cards")
        } else {
            setupObservers(catName + "/Cards")
        }
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
    }


    private fun setupObservers(categoryName: String) {
        mainViewModel.loadImagesStorage(categoryName)

        mainViewModel.repositoryResponseLiveData_ImageStore.observe(viewLifecycleOwner) { resource ->
            this.list = resource.asReversed()
            setAdapter(list!!)
        }


    }

    private fun setAdapter(listA: List<StorageReference>) {
        var adapter = CardAdapter(listA, activity, object :
            OnItemClickListener_Gif {
            override fun onClick(position: Int) {
                val bun = Bundle()
                bun.putString("type", "Cards")
                bun.putString("catName", catName)
                bun.putInt("position", position)
                AppUtils.changeFragment(requireActivity(), R.id.nav_contentPreview, bun)
//                Toast.makeText(
//                    requireContext(),
//                    "Clicked On Cards     $position",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

        })
        binding.gifRecyclerview.adapter = adapter
    }


}


