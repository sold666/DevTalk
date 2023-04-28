package com.dev_talk.main.my_chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dev_talk.R
import com.dev_talk.databinding.FragmentPersonalChatsBinding
import com.dev_talk.main.structures.Chat
import com.dev_talk.main.structures.Profession
import com.google.android.material.tabs.TabLayout


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
        val searchView =
            binding.personalChatsToolbar.menu.findItem(R.id.menu_search)?.actionView as SearchView
        val searchBarMenu = binding.personalChatsToolbar.menu
        val searchViewIcon: ImageView =
            searchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
        searchViewIcon.layoutParams = LinearLayout.LayoutParams(0, 0)
        searchView.apply {
            isIconified = false
            maxWidth = Integer.MAX_VALUE
            queryHint = getString(R.string.default_query_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    searchBarMenu.findItem(R.id.menu_search)?.collapseActionView()
                    filterData("")
                    binding.chatsWithCategory.findViewById<TextView>(R.id.no_chats_detected).visibility = View.GONE
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    filterData(query)
                    return true
                }

                private fun filterData(query: String?) {
                    val myAdapter =
                        binding.chatsWithCategory.findViewById<RecyclerView>(R.id.list_with_my_chats).adapter as PersonalChatsAdapter
                    myAdapter.filter.filter(query)
                    if (myAdapter.itemCount == 0 && !query.equals("")) {
                        binding.chatsWithCategory.findViewById<TextView>(R.id.no_chats_detected).visibility = View.VISIBLE
                    }
                    else {
                        binding.chatsWithCategory.findViewById<TextView>(R.id.no_chats_detected).visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun setUpViewPager2(
        viewPager: ViewPager2,
        tabLayout: TabLayout,
        data: List<Profession>
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

    private fun getProfessions(): List<Profession> {
        return arrayListOf(
            Profession(
                "Profession №1",
                arrayListOf(
                    Chat(
                        R.drawable.ic_person,
                        "C++",
                        "text text text text text text text text text text text text text text text"
                    ),
                    Chat(
                        R.drawable.ic_person,
                        "Java",
                        "text text text text text text text text text text text text text text text"
                    ),
                    Chat(
                        R.drawable.ic_person,
                        "C",
                        "text text text text text text text text text text text text text text text"
                    ),
                    Chat(
                        R.drawable.ic_person,
                        "Kotlin",
                        "text text text text text text text text text text text text text text text"
                    ),
                    Chat(
                        R.drawable.ic_person,
                        "F",
                        "text text text text text text text text text text text text text text text"
                    ),
                    Chat(
                        R.drawable.ic_person,
                        "Ruby",
                        "text text text text text text text text text text text text text text text"
                    ),
                    Chat(
                        R.drawable.ic_person,
                        "Go",
                        "text text text text text text text text text text text text text text text"
                    ),
                )
            ),
            Profession(
                "Profession №2",
                arrayListOf(
                    Chat(
                        R.drawable.ic_person,
                        "Css",
                        "text text text text text text text text text text text text text text text"
                    ),
                    Chat(
                        R.drawable.ic_person,
                        "Html",
                        "text text text text text text text text text text text text text text text"
                    )
                )
            ),
            Profession(
                "Profession №3",
                arrayListOf(
                    Chat(
                        R.drawable.ic_person,
                        "Selenide",
                        "text text text text text text text text text text text text text text text"
                    ),
                    Chat(
                        R.drawable.ic_person,
                        "Selenium",
                        "text text text text text text text text text text text text text text text"
                    ),
                    Chat(
                        R.drawable.ic_person,
                        "Java",
                        "text text text text text text text text text text text text text text text"
                    )
                )
            )
        )
    }
}
