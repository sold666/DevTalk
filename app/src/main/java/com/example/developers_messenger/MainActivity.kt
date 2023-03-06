package com.example.developers_messenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.developers_messenger.databinding.ActivityMainBinding
import com.example.developers_messenger.view_pager_2_adapters.MainPageAdapter

class MainActivity: AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setUpTabBar()
    }

    private fun setUpTabBar() {
        val navigationView = mainBinding.mainBottomNavView
        val viewPager = mainBinding.mainContent
        viewPager.isUserInputEnabled = false; // delete scroll
        viewPager.adapter = MainPageAdapter(this, navigationView.menu.size())
        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.recommended_chats -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.my_chats -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.profile -> {
                    viewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }
        navigationView.selectedItemId = R.id.my_chats // setting default screen
    }
}