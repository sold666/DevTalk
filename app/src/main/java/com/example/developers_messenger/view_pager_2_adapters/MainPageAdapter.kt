package com.example.developers_messenger.view_pager_2_adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.developers_messenger.*

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

    private fun getChats(): ArrayList<Profession> {
        return arrayListOf(
            Profession(
                "Profession №1",
                arrayListOf(
                    Chat(
                        R.drawable.my_chats_navigation_icon,
                        "C++"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation_icon,
                        "Java"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation_icon,
                        "C"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation_icon,
                        "Kotlin"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation_icon,
                        "F"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation_icon,
                        "Ruby"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation_icon,
                        "Go"
                    ),
                )
            ),
            Profession(
                "Profession №2",
                arrayListOf(
                    Chat(
                        R.drawable.default_person_icon,
                        "Css"),
                    Chat(
                        R.drawable.default_person_icon,
                        "Html")
                )
            ),
            Profession(
                "Profession №3",
                arrayListOf(
                    Chat(
                        R.drawable.add_link_in_profile_icon,
                        "Selenide"),
                    Chat(
                        R.drawable.add_link_in_profile_icon,
                        "Selenium"),
                    Chat(
                        R.drawable.add_link_in_profile_icon,
                        "Java")
                )
            )
        )
    }

    private fun getRecommendedChats(): ArrayList<Chat> {
        return arrayListOf(
            Chat(
                R.drawable.my_chats_navigation_icon,
                "Python"),
            Chat(
                R.drawable.default_person_icon,
                "Java Script"),
            Chat(
                R.drawable.add_link_in_profile_icon,
                "Assembler")
        )
    }
}
