package com.modlueinfotech.allwishesgif.ui.activites.gif

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.examp.allwishes.R
import com.examp.allwishes.databinding.FragmentGifBinding
import com.examp.allwishes.ui.data.api.FirebaseHelper
import com.examp.allwishes.ui.fragment.FrameEditFragment
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.util.OnItemClickListener_Gif
import com.examp.allwishes.ui.viewmodel.HomeViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.modlueinfotech.allwishesgif.adapters.recyclerview.GifCardAdapter

class Frames(var catName: String) : Fragment() {

    lateinit var binding: FragmentGifBinding
    private lateinit var mainViewModel: HomeViewModel


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
        setupObservers(catName + "/Frames")


        return binding.root
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())
        mainViewModel =
            ViewModelProvider(requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
    }


    private fun setupObservers(categoryName: String) {
        mainViewModel.loadImagesStorage(categoryName)

        mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity()) { resource ->
            this.list = resource.asReversed()
            setAdapter(list!!)
        }


    }

    private fun setAdapter(listA: List<StorageReference>) {
        var adapter = GifCardAdapter(listA, requireContext(), object :
            OnItemClickListener_Gif {
            override fun onClick(position: Int) {
                val b = Bundle()
                FrameEditFragment.any = listA.get(position)
                AppUtils.changeFragment(requireActivity(), R.id.nav_frameEdit, b)
                Toast.makeText(requireContext(), "Clicked On Gif", Toast.LENGTH_SHORT).show()
            }
        })
        binding.gifRecyclerview.adapter = adapter
    }


}


