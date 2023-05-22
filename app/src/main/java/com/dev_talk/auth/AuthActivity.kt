package com.dev_talk.auth

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.dev_talk.R
import com.dev_talk.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }
    var isAppRestarted = false
    private val WIFI_SETTINGS_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            isAppRestarted = true
        }
        if (hasConnection(this@AuthActivity)) {
            binding = ActivityAuthBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Navigation.setViewNavController(binding.navHostFragment, navController)
        } else {
            showNoInternetDialog()
        }
    }

    override fun onStop() {
        super.onStop()
        isAppRestarted = false
    }

    private fun hasConnection(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }

    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this@AuthActivity)
        builder.setTitle(R.string.alert_title_for_check_internet)
        builder.setMessage(R.string.alert_message_for_check_internet)
        builder.setPositiveButton(
            R.string.alert_positive_button_for_check_internet
        ) { _, _ ->
            startActivityForResult(
                Intent(Settings.ACTION_WIFI_SETTINGS),
                WIFI_SETTINGS_REQUEST_CODE
            )
        }
        builder.setNegativeButton(
            R.string.alert_negative_button_for_delete_profile
        ) { _, _ ->
            finish()
        }
        builder.setOnCancelListener {
            finish()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == WIFI_SETTINGS_REQUEST_CODE) {
            finish()
        }
    }
}
