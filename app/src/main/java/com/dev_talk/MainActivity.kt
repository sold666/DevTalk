package com.dev_talk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev_talk.databinding.ActivityMainBinding
import com.dev_talk.view_pager_2_adapters.MainPageAdapter

class MainActivity: AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setUpNavigationView()
    }

    private fun setUpNavigationView() {
        mainBinding.mainContent.isUserInputEnabled = false; // delete scroll
        mainBinding.mainContent.adapter = MainPageAdapter(this, mainBinding.mainBottomNavView.menu.size())
        mainBinding.mainBottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.recommended_chats -> {
                    mainBinding.mainContent.currentItem = 0
                    true
                }
                R.id.my_chats -> {
                    mainBinding.mainContent.currentItem = 1
                    true
                }
                R.id.profile -> {
                    mainBinding.mainContent.currentItem = 2
                    true
                }
                else -> false
            }
        }
        mainBinding.mainBottomNavView.selectedItemId = R.id.my_chats // setting default screen
    }
}
