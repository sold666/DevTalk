package com.dev_talk.main.my_chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dev_talk.R
import com.dev_talk.databinding.FragmentPersonalChatsBinding
import com.dev_talk.main.structures.Chat
import com.dev_talk.main.structures.Profession
import com.google.android.material.tabs.TabLayout

private const val DEFAULT_LIST_PROFESSIONS_KEY = "professions"

class PersonalChatsFragment : Fragment() {
    private lateinit var binding: FragmentPersonalChatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalChatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val data = getProfessions()
            setUpViewPager2(viewPager = chatsWithCategory, tabLayout = professions, data = data)
            for ((index, currentProfession) in data.withIndex()) {
                if (index < 3) {
                    professions.getTabAt(index)?.text = currentProfession.profession
                }
            }
            professions.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab) {
                    chatsWithCategory.currentItem = tab.position
                }
            })
            setUpSearchView()
        }
    }

    private fun getProfessionsNames(): List<String> {
        val listWithNames = ArrayList<String>()
        with(binding.professions) {
            for (i in 0 until tabCount) {
                listWithNames.add(getTabAt(i)?.text.toString())
            }
        }
        return listWithNames
    }

    private fun setUpSearchView() {
        val searchView = binding.personalChatsToolbar.menu.findItem(R.id.menu_search)?.actionView as SearchView
        val searchBarMenu = binding.personalChatsToolbar.menu
        searchView.apply {
            maxWidth = Integer.MAX_VALUE;
            queryHint = getString(R.string.default_query_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    searchBarMenu.findItem(R.id.menu_search)?.collapseActionView()
                    filterData("")
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    filterData(query)
                    return true
                }

                private fun filterData(query: String?) {
                    Log.d("hehe", "123")
                    val myAdapter = binding.chatsWithCategory.findViewById<RecyclerView>(R.id.list_with_my_chats).adapter as PersonalChatsAdapter

                    myAdapter.filter.filter(query)
                }
            })
        }
    }

    private fun setUpViewPager2(
        viewPager: ViewPager2,
        tabLayout: TabLayout,
        data: java.util.ArrayList<Profession>
    ) {
        viewPager.apply {
            adapter = PersonalChatsAdapterViewPager(
                activity = requireActivity(),
                itemCount = tabLayout.tabCount,
                data = data,
                tabNames = getProfessionsNames()
            )
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }
    }

    private fun getProfessions(): ArrayList<Profession> {
        return arrayListOf(
            Profession(
                "Profession №1",
                arrayListOf(
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "C++"
                    ),
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "Java"
                    ),
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "C"
                    ),
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "Kotlin"
                    ),
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "F"
                    ),
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "Ruby"
                    ),
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "Go"
                    ),
                )
            ),
            Profession(
                "Profession №2",
                arrayListOf(
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "Css"
                    ),
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "Html"
                    )
                )
            ),
            Profession(
                "Profession №3",
                arrayListOf(
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "Selenide"
                    ),
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "Selenium"
                    ),
                    Chat(
                        R.drawable.ic_my_chats_navigation,
                        "Java"
                    )
                )
            )
        )
    }
}
