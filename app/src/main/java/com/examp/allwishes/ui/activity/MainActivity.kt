package com.examp.allwishes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.examp.allwishes.R
import com.examp.allwishes.databinding.ActivityMainBinding
import com.examp.allwishes.ui.fragment.MainFragment
import com.examp.allwishes.ui.util.AppUtils


class MainActivity : AppCompatActivity() {


    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        openMainFragment(MainFragment())
    }

    private fun openMainFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_content_main, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val destination = navController.currentDestination
        if (destination?.id == R.id.nav_main) {

//                AdUtils.showFullAd(this, object : AdUtils.AdListener {
//                    override fun onComplete() {
            AppUtils.fullExitScreen(this)
//                    }
//                })

        } else {
            super.onBackPressed()
        }
    }

}