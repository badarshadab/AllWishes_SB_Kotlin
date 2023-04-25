package com.examp.allwishes.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.examp.allwishes.R
import com.examp.allwishes.databinding.ActivityHolidaysListBinding
import com.examp.allwishes.ui.fragment.HolidayMainFragment
import com.examp.allwishes.ui.util.AppUtils
import com.google.firebase.storage.StorageReference

class Holidays_List : AppCompatActivity() {


    lateinit var binding: ActivityHolidaysListBinding
//    lateinit var toolbar: Toolbar

    //    private var arrayList: ArrayList<EventByMonth>? = null
    companion object {
        lateinit var holidaysList: Holidays_List
        lateinit var storageRef: StorageReference
        lateinit var activity: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHolidaysListBinding.inflate(layoutInflater)
        val view = binding.root
        activity = this
        holidaysList = this
        setContentView(view)

        openMainFragment(HolidayMainFragment())


    }


    private fun openMainFragment(fragment: Fragment) {
        val b = Bundle()
        AppUtils.changeFragment(this , R.id.nav_home , b)
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.nav_host_fragment_content_main, fragment)
//        transaction.commit()
    }
}