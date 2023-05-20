package com.dev_talk.main.my_chats

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dev_talk.R
import com.dev_talk.common.structures.ProfessionDto
import com.dev_talk.databinding.FragmentPersonalChatsBinding
import com.dev_talk.dto.ChatDto
import com.dev_talk.main.structures.Chat
import com.dev_talk.main.structures.Profession
import com.dev_talk.utils.DATABASE_URL
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.getProfessions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class PersonalChatsFragment : Fragment() {
    private lateinit var binding: FragmentPersonalChatsBinding
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var chatProfessions: ArrayList<Profession>
    private val professions = getProfessions()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalChatsBinding.inflate(inflater)
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        chatProfessions = arrayListOf()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            addTabs()
            setUpViewPager2(
                viewPager = binding.chatsWithCategory,
                tabLayout = binding.professions,
                data = chatProfessions
            )
            professions.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab) {
                    chatsWithCategory.currentItem = tab.position
                }
            })
            setUpSearchView()
            getDataFromDatabase()
        }
    }

    private fun getDataFromDatabase() {
        val ref = db.child("chats")
        val chatListener = object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (it in snapshot.children) {
                    if (it.child("participants").child(auth.currentUser?.uid.toString()).exists()) {
                        val chat = it.getValue<ChatDto>()

                        val prof = professions.firstOrNull { p ->
                            p.tags.find { t -> t.name == chat?.tag } != null
                        }
                        if (prof != null) {
                            val alreadyExistedProfession =
                                chatProfessions.find { p -> p.profession == prof.name }
                            val newChat = Chat(
                                R.drawable.default_avatar_chat,
                                chat?.name!!,
                                "last message"
                            )
                            if (alreadyExistedProfession != null) {
                                if (alreadyExistedProfession.chats.find { c -> c.name == newChat.name } == null) {
                                    alreadyExistedProfession.chats.add(newChat)
                                }
                            } else {
                                chatProfessions.add(Profession(prof.name, mutableListOf(newChat)))
                            }

                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        ref.addValueEventListener(chatListener)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun addTabs() {

        val selectedProfessions = arguments?.getStringArrayList("professionList")

        selectedProfessions!!.forEach { it ->
            binding.professions.addTab(
                binding.professions.newTab()
                    .setText(it)
            )
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val tabList: ArrayList<String> = arrayListOf()
        for (i in 0 until binding.professions.tabCount) {
            tabList.add(binding.professions.getTabAt(i)?.text.toString())
        }
        outState.putStringArrayList("tabNames", tabList)
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
                    binding.chatsWithCategory.findViewById<TextView>(R.id.no_chats_detected).visibility =
                        View.GONE
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
                }
            })
        }
    }

    private fun setUpViewPager2(
        viewPager: ViewPager2,
        tabLayout: TabLayout,
        data: List<Profession>
    ) {
        val tabs = getProfessionsNames()
        val adapter = PersonalChatsAdapterViewPager(
            activity = requireActivity(),
            itemCount = tabLayout.tabCount,
            data = data,
            tabNames = tabs
        )

        viewPager.adapter = adapter

        viewPager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }
    }
}
