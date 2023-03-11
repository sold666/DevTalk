package com.dev_talk.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dev_talk.main.my_chats.PersonalChatsFragment
import com.dev_talk.main.profile.ProfileFragment
import com.dev_talk.main.recommended_chats.RecommendedChatsFragment
import com.dev_talk.main.structures.Chat
import com.dev_talk.main.structures.ChatsNavigation
import com.dev_talk.main.structures.Profession

class MainPageAdapter(activity: FragmentActivity, private val itemCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            ChatsNavigation.RECOMMENDED.position -> {
                RecommendedChatsFragment.newInstance(getRecommendedChats())
            }
            ChatsNavigation.PROFILE.position -> {
                ProfileFragment.newInstance(getProfessions())
            }
            else -> {
                PersonalChatsFragment.newInstance(getProfessions())
            }
        }
    }

    private fun getProfessions(): ArrayList<Profession> {
        return arrayListOf(
            Profession(
                "Profession №1",
                arrayListOf(
                    Chat(
                        R.drawable.my_chats_navigation,
                        "C++"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation,
                        "Java"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation,
                        "C"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation,
                        "Kotlin"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation,
                        "F"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation,
                        "Ruby"
                    ),
                    Chat(
                        R.drawable.my_chats_navigation,
                        "Go"
                    ),
                )
            ),
            Profession(
                "Profession №2",
                arrayListOf(
                    Chat(
                        R.drawable.default_person,
                        "Css"
                    ),
                    Chat(
                        R.drawable.default_person,
                        "Html"
                    )
                )
            ),
            Profession(
                "Profession №3",
                arrayListOf(
                    Chat(
                        R.drawable.add_new_chat_btn,
                        "Selenide"
                    ),
                    Chat(
                        R.drawable.add_new_chat_btn,
                        "Selenium"
                    ),
                    Chat(
                        R.drawable.add_new_chat_btn,
                        "Java"
                    )
                )
            )
        )
    }

    private fun getRecommendedChats(): ArrayList<Chat> {
        return arrayListOf(
            Chat(
                R.drawable.my_chats_navigation,
                "Python"
            ),
            Chat(
                R.drawable.default_person,
                "Java Script"
            ),
            Chat(
                R.drawable.add_new_chat_btn,
                "Assembler"
            )
        )
    }
}
