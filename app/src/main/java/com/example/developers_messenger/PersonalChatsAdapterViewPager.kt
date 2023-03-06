package com.example.developers_messenger

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class PersonalChatsAdapterViewPager(activity: FragmentActivity, private val itemCount: Int, private val data: ArrayList<Chat>, private val tabNames: ArrayList<String>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        val currentProfession = tabNames[position]
        return PersonalChatListFragment(data[findIndexOfProfession(currentProfession)])
    }

    private fun findIndexOfProfession(newProfession: String): Int {
    for (i in 0 until data.size) {
        if (data[i].profession == newProfession) {
            return i
        }
    }
    return 0
}
}