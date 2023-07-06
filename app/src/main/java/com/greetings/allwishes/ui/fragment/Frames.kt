package com.greetings.allwishes.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.greetings.allwishes.R
import com.greetings.allwishes.databinding.FragmentGifBinding
import com.greetings.allwishes.ui.data.api.FirebaseHelper
import com.greetings.allwishes.ui.util.AppUtils
import com.greetings.allwishes.ui.util.OnItemClickListener_Gif
import com.greetings.allwishes.ui.viewmodel.HomeViewModel
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.greetings.allwishes.adapters.recyclerview.FramesAdapter

class Frames(var catName: String) : Fragment() {

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
            setupObservers(trending_cat + "/Frames")
        } else {
            setupObservers(catName + "/Frames")
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
            binding.inclidegifid.progresbarid.visibility = View.GONE
            setAdapter(list!!)
        }


    }

    private fun setAdapter(listA: List<StorageReference>) {
        var adapter = FramesAdapter(listA, activity, object :
            OnItemClickListener_Gif {
            override fun onClick(position: Int) {
                val b = Bundle()
                FrameEditFragment.any = listA.get(position)
                AppUtils.changeFragment(requireActivity(), R.id.nav_frameEdit, b)
//                Toast.makeText(requireContext(), "Clicked On Gif", Toast.LENGTH_SHORT).show()
            }
        })
        binding.gifRecyclerview.adapter = adapter
    }


}


