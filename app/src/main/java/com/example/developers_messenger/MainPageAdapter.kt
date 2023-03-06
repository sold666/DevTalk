package com.example.developers_messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class MainPageAdapter(activity: FragmentActivity, private val itemCount: Int): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = RecommendedChatsFragment()
                fragment.arguments = Bundle().apply {
                    putParcelableArrayList(RECOMMENDED_LIST_PROFESSIONS_KEY, getRecommendedChats())
                }
                fragment
            }
            1 -> {
                val fragment = PersonalChatsFragment()
                fragment.arguments = Bundle().apply {
                    putParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY, getChats())
                }
                fragment
            }
            2 -> {
                val fragment = ProfileFragment()
                fragment.arguments = Bundle().apply {
                    putParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY, getChats())
                }
                fragment
            }
            else -> {
                PersonalChatsFragment()
            }
        }
    }

    private fun getChats(): ArrayList<Chat> {
        return arrayListOf(
            Chat(
                R.drawable.my_chats_navigation_icon,
                "Profession №1",
                arrayListOf("C++", "Java", "C", "Kotlin", "t", "tt", "uu")),
            Chat(
                R.drawable.default_person_icon,
                "Profession №2",
                arrayListOf("Css", "Html")),
            Chat(
                R.drawable.add_link_in_profile_icon,
                "Profession №3",
                arrayListOf("Selenide", "Selenium", "Java")
            )
        )
    }

    private fun getRecommendedChats(): ArrayList<RecommendedChat> {
        return arrayListOf(
            RecommendedChat(
                R.drawable.my_chats_navigation_icon,
                "Python"),
            RecommendedChat(
                R.drawable.default_person_icon,
                "Java Script"),
            RecommendedChat(
                R.drawable.add_link_in_profile_icon,
                "Assembler")
            )
    }
}
