package com.dev_talk.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.dev_talk.R
import com.dev_talk.databinding.AuthActivityBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: AuthActivityBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AuthActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        Navigation.setViewNavController(binding.navHostFragment, navController)
    }
}
