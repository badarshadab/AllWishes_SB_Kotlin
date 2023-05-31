package com.examp.allwishes.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.examp.allwishes.R
import com.examp.allwishes.databinding.ActivityMainBinding
import com.examp.allwishes.ui.fragment.MainFragment
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
        openMainFragment(MainFragment())
        val mDrawerToggle = ActionBarDrawerToggle(
            this,
            b.drawerLayout,
            b.appBar.toolbar.toolbar,
            R.string.nav_app_bar_open_drawer_description,
            R.string.navigation_drawer_close
        )
        mDrawerToggle.isDrawerIndicatorEnabled = false //disable "hamburger to arrow" drawable
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_home) //set your own
        b.appBar.toolbar.toolbar.setNavigationOnClickListener { b.drawerLayout.open() }
        val font = Typeface.createFromAsset(assets, "fonts/QUMPELLKANO.OTF")
        b.appBar.toolbar.tooText.typeface = font
        val view = b.root
        setContentView(view)

        activity = this
    }

    override fun onResume() {
        super.onResume()
//        setDrawerLock()
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

    private fun openMainFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {

        val navController = findNavController(R.id.nav_host_fragment)
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