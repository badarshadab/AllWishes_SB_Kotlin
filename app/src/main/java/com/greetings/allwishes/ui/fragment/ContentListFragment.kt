package com.greetings.allwishes.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.StorageReference
import com.greetings.allwishes.R
import com.greetings.allwishes.databinding.FragmentContentListBinding
import com.greetings.allwishes.modelfactory.MyViewModelFactory
import com.greetings.allwishes.ui.adapter.GifImageAdapter
import com.greetings.allwishes.ui.adapter.QuotesListAdapter
import com.greetings.allwishes.ui.data.api.FirebaseHelper
import com.greetings.allwishes.ui.util.AppUtils
import com.greetings.allwishes.ui.viewmodel.FramesViewModel
import com.greetings.allwishes.ui.viewmodel.HomeViewModel
import com.greetings.allwishes.ui.viewmodel.QuoteViewModel
import com.greetings.allwishes.util.AdUtils
import com.hindishyari.shyari.viewModels.CardViewModel


class ContentListFragment : Fragment() {
    private lateinit var b: FragmentContentListBinding
    private lateinit var mainViewModel: HomeViewModel
    private lateinit var cardsViewModel: FramesViewModel
    private lateinit var quotesViewModel: QuoteViewModel
    private var list: List<StorageReference>? = null
    private var name: String = ""
    private var type: String = ""
    private var nameType: String = ""

    lateinit var activity: Activity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = name + " " + type
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
        b = FragmentContentListBinding.inflate(inflater, container, false)

        name = arguments?.getString("catName").toString()
        type = arguments?.getString("type").toString()

        setupViewModel()
        AdUtils.showNativeBanner(requireActivity(), b.adContainer.nativeAdContainer)
        nameType = "DailyWishes/" + name + "/" + type
        setupObservers(nameType)

        if (type.equals("Quotes")) {
            b.shimmerLayImages.visibility = View.GONE
            b.rv.layoutManager = GridLayoutManager(requireContext(), 1)
//            b.shimmerLayQuotes.startShimmer()

        } else {
            b.shimmerLayQuotes.visibility = View.GONE
            b.rv.layoutManager = GridLayoutManager(requireContext(), 3)
        }
        return b.root
    }

    override fun onResume() {
        if (type.equals("Quotes")) {
            b.shimmerLayImages.visibility = View.GONE
            b.shimmerLayQuotes.visibility = View.VISIBLE
            b.rv.layoutManager = GridLayoutManager(requireContext(), 1)
//            b.shimmerLayQuotes.startShimmer()

        } else {
            b.shimmerLayQuotes.visibility = View.GONE
            b.rv.layoutManager = GridLayoutManager(requireContext(), 3)
        }
        super.onResume()
    }

    private fun setupViewModel() {
        val myViewModelFactory = MyViewModelFactory(FirebaseHelper())

        mainViewModel =
            ViewModelProvider(requireActivity(), myViewModelFactory)[HomeViewModel::class.java]
        quotesViewModel =
            ViewModelProvider(this)[QuoteViewModel::class.java]
    }

    private fun setQuotesAdapter(list: List<String>) {
        if (list != null) {
            b.shimmerLayQuotes.stopShimmer()
            b.rv.visibility = View.VISIBLE
            b.shimmerLayQuotes.visibility = View.GONE
            b.pb.visibility = View.GONE
        }
        b.rv.adapter = QuotesListAdapter(
            activity,
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

    private fun setImageAdapter(resource: List<StorageReference>) {
        if (resource != null) {
            b.shimmerLayImages.stopShimmer()
            b.rv.visibility = View.VISIBLE
            b.shimmerLayImages.visibility = View.GONE
            b.pb.visibility = View.GONE
        }
        this.list = resource
        b.rv.adapter = this.list?.let { it1 ->
            GifImageAdapter(
                activity,
                it1, object : GifImageAdapter.RecyclerViewClickListener {
                    override fun onClick(
                        view: View?,
                        position: Int
                    ) {
                        val b = Bundle()
                        b.putString("type", type)
                        b.putString("wishesName", "" + name)
                        b.putString("catName", "DailyWishes")
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
//        b.rv.adapter?.notifyDataSetChanged()
    }

    private fun setupObservers(categoryName: String) {

        if (type.equals("Quotes")) {
            quotesViewModel.getQuotes(categoryName)
            quotesViewModel.quotes.observe(requireActivity()) { list ->
                setQuotesAdapter(list)
            }
            // if we use `Dispatchers.Main` as a coroutine context next two lines will be executed on UI thread.

        } else if(type.equals("Gifs")) {
            mainViewModel.loadImagesStorage(categoryName)
            mainViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity())
            { resource ->
                // if we use `Dispatchers.Main` as a coroutine context next two lines will be executed on UI thread.
                setImageAdapter(resource.asReversed())
            }
        }
        else if(type.equals("Cards")) {

            val cardViewModel : CardViewModel by lazy { ViewModelProvider(this)[CardViewModel::class.java] }
            cardViewModel.getCategoryWiseCards(categoryName).observe(viewLifecycleOwner, Observer {
                setImageAdapter(it)
                println("bgList$it")
            })

//            cardsViewModel.loadImagesStorage(categoryName)
//            cardsViewModel.repositoryResponseLiveData_ImageStore.observe(requireActivity())
//            { resource ->
//                // if we use `Dispatchers.Main` as a coroutine context next two lines will be executed on UI thread.
//                setImageAdapter(resource.asReversed())
//            }
        }

    }




}