package com.examp.allwishes.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.examp.allwishes.databinding.ActivitySavedHolidayGreetingsBinding

class SavedHolidayGreetings : AppCompatActivity() {


    lateinit var binding: ActivitySavedHolidayGreetingsBinding

    companion object {
        lateinit var activity: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySavedHolidayGreetingsBinding.inflate(layoutInflater)
        val view = binding.root

        activity = this

        setContentView(view)

    }
}