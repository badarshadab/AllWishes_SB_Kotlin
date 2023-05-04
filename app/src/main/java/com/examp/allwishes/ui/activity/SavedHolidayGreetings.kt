package com.examp.allwishes.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.examp.allwishes.R
import com.examp.allwishes.databinding.ActivitySavedHolidayGreetingsBinding
import com.examp.allwishes.ui.util.AppUtils

class SavedHolidayGreetings : AppCompatActivity() {


    lateinit var binding: ActivitySavedHolidayGreetingsBinding

    companion object {
        lateinit var activity: Activity
    }
    var from:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySavedHolidayGreetingsBinding.inflate(layoutInflater)
        val view = binding.root
        var bundle :Bundle ?=intent.extras
        from = bundle!!.getString("from")
        activity = this
//        val b = Bundle()
//        b.putString("from" , from)
//        AppUtils.changeFragment(this , R.id.nav_savedfrag , b)

        setContentView(view)

    }
}