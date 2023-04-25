package com.examp.allwishes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.examp.allwishes.databinding.FragmentHolidayBinding
import com.examp.allwishes.ui.activity.Holidays_List
import com.examp.allwishes.ui.adapter.HolidayAdapter
import com.examp.allwishes.ui.model.Event
import com.examp.allwishes.ui.model.EventByMonth
import com.examp.allwishes.ui.viewmodel.HolidayViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HolidayFragment(val pos: Int, val title: String) : Fragment() {

    lateinit var _binding: FragmentHolidayBinding
    private val binding get() = _binding!!
    private lateinit var mainViewModel: HolidayViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHolidayBinding.inflate(inflater, container, false)
        val view = binding.root
        activity?.setTitle("Holiday List")

        _binding.progressBar.visibility = View.VISIBLE
        mainViewModel = ViewModelProvider(requireActivity())[HolidayViewModel::class.java]

        val arrayList = mainViewModel.getComModel()
        if (arrayList == null) {
            observEvents(title)
        } else {
            getMonthEvents(arrayList, title)
        }


        return view
    }

    private fun observEvents(title: String) {
        GlobalScope.launch {
            mainViewModel.arrayListLiveData?.collect { model ->
                getMonthEvents(model, title)
            }
        }
    }

    private fun getMonthEvents(arrayList: ArrayList<EventByMonth>, title: String) {
        arrayList.let {
            if (arrayList.size >= pos) {
                if (title.equals("All")) {
                    combileAll(arrayList)
                } else {
                    val eventByMonth = arrayList.get(pos - 1)
                    setDataInList(eventByMonth.events)
                }
            }
        }
    }

    private fun combileAll(arrayList: ArrayList<EventByMonth>) {
        var eventList = ArrayList<Event>()
        if (eventList != null) {
            _binding.progressBar.visibility = View.GONE
        }
        for (events in arrayList) {
            events.events?.let { eventList.addAll(it) }
        }
        setDataInList(eventList)
    }

    private fun setDataInList(event: ArrayList<Event>?) {
        GlobalScope.launch(Dispatchers.Main) {
            val adapter = HolidayAdapter(requireActivity(), event)
            _binding.rv.adapter = adapter
            _binding.rv.layoutManager =
                LinearLayoutManager(Holidays_List.activity, LinearLayoutManager.VERTICAL, false)
        }
    }
}