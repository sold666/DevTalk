package com.dev_talk.main.view_pager_2_adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dev_talk.main.PersonalChatListFragment
import com.dev_talk.main.structures.Profession

class PersonalChatsAdapterViewPager(activity: FragmentActivity, private val itemCount: Int, private val data: List<Profession>, private val tabNames: List<String>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment {
        val currentProfession = tabNames[position]
        return PersonalChatListFragment.newInstance(data[findIndexOfProfession(currentProfession)])
    }

    private fun findIndexOfProfession(profession: String): Int {
        for((index, professionName) in data.withIndex()) {
            if (professionName.profession == profession) {
                return index
            }
        }
        return 0
    }
}
