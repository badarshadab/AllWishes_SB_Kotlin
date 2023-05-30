package com.examp.allwishes.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.examp.allwishes.databinding.HolidaymainfragmentBinding
import com.examp.allwishes.ui.adapter.HolidayViewpagerAdapter
import com.examp.allwishes.ui.model.EventByMonth
import com.examp.allwishes.ui.util.AppUtils
import com.examp.allwishes.ui.viewmodel.HolidayViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HolidaymainfragmentBinding.inflate(inflater, container, false)

        toolbar = _binding.toolbar.toolbar
        AppUtils.setUpToolbar(
            requireActivity() as AppCompatActivity,
            toolbar,
            "Holiday List",
            true
        )
        //added after this bug
        //java.lang.IllegalStateException: Fragment no longer exists for key f0: index 1
        //ref https://stackoverflow.com/questions/45734711/java-lang-illegalstateexception-fragment-no-longer-exists-for-key-f0-index-1
        _binding.vp.isSaveEnabled = false

        viewpager = _binding.vp
        tablayout = _binding.tl

        _binding.progressBar.visibility = View.VISIBLE
        mainViewModel = ViewModelProvider(requireActivity())[HolidayViewModel::class.java]

//        AdUtils.showNativeBanner(requireActivity(), _binding.nativeAdContainer)

        val arrayList = mainViewModel.getComModel()
        if (arrayList == null) {
            observEvents()
        } else {
            _binding.progressBar.visibility = View.GONE
            getMonthName(arrayList)
        }


        tablayout.setSelectedTabIndicatorColor(Color.parseColor("#1B7DE6"))
        tablayout.setSelectedTabIndicatorHeight(((7 * getResources().getDisplayMetrics().density).toInt()))
        tablayout.setTabTextColors(Color.parseColor("#A3A0A0"), Color.parseColor("#1B7DE6"))



        return _binding.root
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

//                        val monthName = event.date?.let { it1 -> getSString(it1, 3, 5) }
//                        val date = Integer.parseInt(monthName)
//                        month = convertMonth(date)
//                        println("month in  getMonthName" + month)
                        month = event.monthName.toString()

                        monthtablist.add(month)
                        arraylistLocal.add(month)
                    }

                    viewpager.adapter = HolidayViewpagerAdapter(
                        requireActivity().supportFragmentManager,
                        lifecycle,
                        arraylistLocal
                    )
                    for (month in monthtablist) {
//                        println(" month " + month)
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