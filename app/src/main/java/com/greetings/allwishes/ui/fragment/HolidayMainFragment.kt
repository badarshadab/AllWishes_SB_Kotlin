package com.greetings.allwishes.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.greetings.allwishes.databinding.HolidaymainfragmentBinding
import com.greetings.allwishes.ui.adapter.HolidayViewpagerAdapter
import com.greetings.allwishes.ui.model.EventByMonth
import com.greetings.allwishes.ui.viewmodel.HolidayViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.greetings.allwishes.util.AdUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HolidayMainFragment : Fragment() {

    lateinit var _binding: HolidaymainfragmentBinding
    var list = ArrayList<String>()

    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager2

    private lateinit var mainViewModel: HolidayViewModel

    var month: String = ""
    val monthArray = arrayOf(
        "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Holidays List"
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HolidaymainfragmentBinding.inflate(inflater, container, false)


        //added after this bug
        //java.lang.IllegalStateException: Fragment no longer exists for key f0: index 1
        //ref https://stackoverflow.com/questions/45734711/java-lang-illegalstateexception-fragment-no-longer-exists-for-key-f0-index-1
        _binding.vp.isSaveEnabled = false

        viewpager = _binding.vp
        tablayout = _binding.tl

//        _binding.progressBar.visibility = View.VISIBLE
        mainViewModel = ViewModelProvider(requireActivity())[HolidayViewModel::class.java]

        AdUtils.showNativeBanner(requireActivity(), _binding.nativeAdContainer)

        tablayout.setSelectedTabIndicatorColor(Color.parseColor("#1B7DE6"))
        tablayout.setSelectedTabIndicatorHeight(((7 * getResources().getDisplayMetrics().density).toInt()))
        tablayout.setTabTextColors(Color.parseColor("#A3A0A0"), Color.parseColor("#1B7DE6"))

        return _binding.root
    }

    override fun onResume() {

        val arrayList = mainViewModel.getComModel()
        if (arrayList == null) {
            observEvents()
        } else {
            _binding.shimmerLay.stopShimmer()
            viewpager.visibility = View.VISIBLE
            _binding.shimmerLay.visibility = View.GONE
//            _binding.progressBar.visibility = View.GONE
            getMonthName(arrayList)
        }
        super.onResume()
    }

    private fun observEvents() {
        GlobalScope.launch {
            mainViewModel.arrayListLiveData?.collect { model ->
                getMonthName(model)
            }
        }
    }

    val monthtablist: MutableList<String> = ArrayList()

    private fun getMonthName(arrayList: ArrayList<EventByMonth>): String {
        var arraylistLocal = ArrayList<String>()
        arrayList.let {


            lifecycleScope.launch {
                withContext(Dispatchers.Main)
                {

                    monthtablist.add("All")
                    arraylistLocal.add("All")

                    for (event in arrayList) {

                        month = event.monthName.toString()

                        monthtablist.add(month)
                        arraylistLocal.add(month)
                    }
                    _binding.shimmerLay.stopShimmer()
                    viewpager.visibility = View.VISIBLE
                    _binding.shimmerLay.visibility = View.GONE

                    viewpager.adapter = HolidayViewpagerAdapter(
                        this@HolidayMainFragment,
                        arraylistLocal
                    )
                    for (month in monthtablist) {
                        TabLayoutMediator(tablayout, viewpager) { tab, position ->
                            tab.text = monthtablist.get(position)
                        }.attach()
                    }
                }
            }


        }
        return month
    }


}