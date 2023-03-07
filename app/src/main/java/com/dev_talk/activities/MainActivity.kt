package com.dev_talk.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev_talk.R
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
        val currentFragmentActivity = this
        with(mainBinding) {
            mainContent.isUserInputEnabled = false; // delete scroll
            mainContent.adapter = MainPageAdapter(currentFragmentActivity, mainBottomNavView.menu.size())
            mainBottomNavView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.recommended_chats -> {
                        mainContent.currentItem = 0
                        true
                    }
                    R.id.my_chats -> {
                        mainContent.currentItem = 1
                        true
                    }
                    R.id.profile -> {
                        mainContent.currentItem = 2
                        true
                    }
                    else -> false
                }
            }
            mainBottomNavView.selectedItemId = R.id.my_chats // setting default screen
        }
    }
}
