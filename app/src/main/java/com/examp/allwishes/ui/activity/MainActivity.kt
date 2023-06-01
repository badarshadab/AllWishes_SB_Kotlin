package com.examp.allwishes.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.examp.allwishes.R
import com.examp.allwishes.databinding.ActivityMainBinding
import com.examp.allwishes.ui.util.AppUtils


class MainActivity : AppCompatActivity() {


    private lateinit var b: ActivityMainBinding
    lateinit var navController: NavController

    companion object {
        lateinit var activity: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        val mDrawerToggle = ActionBarDrawerToggle(
            this,
            b.drawerLayout,
            b.appBar.toolbar.toolbar,
            R.string.nav_app_bar_open_drawer_description,
            R.string.navigation_drawer_close
        )
        mDrawerToggle.isDrawerIndicatorEnabled = false //disable "hamburger to arrow" drawable
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_home) //set your own
        setSupportActionBar(b.appBar.toolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        b.appBar.toolbar.toolbar.setNavigationOnClickListener { b.drawerLayout.open() }
//        val font = Typeface.createFromAsset(assets, "font/qumpellkano.ttf")
//        b.appBar.toolbar.tooText.typeface = font
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        AppUtils.setupToolbar(b.appBar.toolbar.toolbar, this, b.drawerLayout)
        val view = b.root
        setContentView(view)

        activity = this
    }

    override fun onResume() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.nav_main -> b.appBar.toolbar.toolbar.setNavigationIcon(R.drawable.ic_home)
                else -> b.appBar.toolbar.toolbar.setNavigationIcon(R.drawable.ic_back_new)
            }
        }
        super.onResume()
        setDrawerLock()
    }

    fun setDrawerLock() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id != R.id.nav_main) {
                b.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            } else {
                b.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
    }


    override fun onBackPressed() {

        val navController = findNavController(R.id.nav_host_fragment)
        val destination = navController.currentDestination
        if (destination?.id == R.id.nav_main) {

            AppUtils.fullExitScreen(this)

        } else {
//            AdsHandler.launchReviewPopup(this, object : AdsHandler.ReviewCallBack {
//                override fun onComplete(isSucces: Boolean) {
//                }
//            })
            navController.popBackStack()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, imageData: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageData)
        for (fragment in supportFragmentManager.primaryNavigationFragment!!
            .childFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, imageData)
        }
    }

    fun shareApp(v: View) {
        AppUtils.shareApp(this)
    }

    fun downloaded(v: View) {
        val bun = Bundle()
//                    bun.putString("name", str)
//                    bun.putString("type", "Frame")
        b.drawerLayout.close()
        AppUtils.changeFragment(this, R.id.nav_saved_main, bun)
//        AppUtils.getInstance().shareApp(this)
    }


    fun rateUs(v: View) {
        AppUtils.rateUs(this)
    }

    fun pp(v: View) {
        AppUtils.openUrl(this, getString(R.string.pp_url))
    }

}