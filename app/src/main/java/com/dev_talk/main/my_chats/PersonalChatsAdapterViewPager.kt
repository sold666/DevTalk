package com.dev_talk.main.my_chats

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dev_talk.main.structures.Profession

class PersonalChatsAdapterViewPager(
    activity: FragmentActivity,
    private val itemCount: Int,
    private val data: List<Profession>,
    private val tabNames: List<String>
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        val currentProfession = tabNames[position]
        val inx = findIndexOfProfession(currentProfession)
        if (inx == -1) {
            return PersonalChatListFragment.newInstance(Profession(currentProfession, mutableListOf()))
        } else {
            return PersonalChatListFragment.newInstance(data[inx])
        }
    }

    private fun findIndexOfProfession(profession: String): Int {
        for ((index, professionName) in data.withIndex()) {
            if (professionName.profession == profession) {
                return index
            }
        }
        return -1
    }
}
