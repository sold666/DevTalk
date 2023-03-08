package com.dev_talk.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dev_talk.R
import com.dev_talk.databinding.ActivityMainBinding
import com.dev_talk.structures.ChatsNavigation
import com.dev_talk.view_pager_2_adapters.MainPageAdapter
import com.google.android.material.navigation.NavigationBarView
import java.text.FieldPosition

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
            mainBottomNavView.setOnItemSelectedListener{
                when (it.itemId) {
                    R.id.recommended_chats -> {
                        mainContent.currentItem = ChatsNavigation.RECOMMENDED.position
                        true
                    }
                    R.id.my_chats -> {
                        mainContent.currentItem = ChatsNavigation.PERSONAL.position
                        true
                    }
                    R.id.profile -> {
                        mainContent.currentItem = ChatsNavigation.PROFILE.position
                        true
                    }
                    else -> false
                }
            }
            mainBottomNavView.selectedItemId = R.id.my_chats // setting default screen
        }
    }
}
