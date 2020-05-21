package com.example.newsapp.activities

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapp.adapters.MyViewPagerAdapter
import com.example.newsapp.fragments.AboutTrump
import com.example.newsapp.fragments.BBCNews
import com.example.newsapp.fragments.GermanyNews
import com.example.newsapp.fragments.USNews
import com.example.newsapp.Prefs
import com.example.newsapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var prefs: Prefs? = null
    var bgColor: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = Prefs(this)
         bgColor = prefs!!.backgroundMode
        setUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val darkModeTitle = menu.findItem(R.id.darkModeEnable)
        if (!bgColor!!) {
            darkModeTitle.title = "Enable Dark Mode"
        } else {
            darkModeTitle.title = "Disable Dark Mode"
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.darkModeEnable -> {
                return if (!bgColor!!) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    prefs!!.backgroundMode = true
                    true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    prefs!!.backgroundMode = false
                    true
                }
            }
            R.id.aboutApp ->{
                startActivity(Intent(this,
                    AboutUs::class.java))
                return true
            }
            R.id.rateUs ->{
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                } catch(e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
                }
                return true
            }
            else -> false
        }
    }

    private fun showDialog(): AlertDialog {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialogue,null)
        val dialogBuilder = AlertDialog.Builder(this).setView(dialogView)

            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Try Again", DialogInterface.OnClickListener { _, _ ->
                setUp()
            })
            // negative button text and action
            .setNegativeButton("Close App", DialogInterface.OnClickListener { _, _ ->
                finish()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // show alert dialog
        alert.show()
        alert.window!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.DialogueColor)))
        return alert
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting
        }
    }

    private fun setUp() {

        if (bgColor!!) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        if (isInternetConnected()) {
            val adapter = MyViewPagerAdapter(
                supportFragmentManager
            )
            adapter.addFragment(USNews(), " US News ")
            adapter.addFragment(BBCNews(), " BBC News ")
            adapter.addFragment(AboutTrump(), " About Trump ")
            adapter.addFragment(GermanyNews(), " Germany News ")
            viewPager.adapter = adapter
            tabBar.setupWithViewPager(viewPager)
        } else {
            showDialog()
        }
    }

}
