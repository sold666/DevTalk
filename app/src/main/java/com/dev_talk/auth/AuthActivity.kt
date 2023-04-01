package com.dev_talk.auth

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            isAppRestarted = true
        }
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Navigation.setViewNavController(binding.navHostFragment, navController)
    }

    override fun onStop() {
        super.onStop()
        isAppRestarted = false
    }
}
