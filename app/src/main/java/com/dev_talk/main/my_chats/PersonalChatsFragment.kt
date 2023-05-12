package com.dev_talk.main.my_chats

import android.os.Bundle
import android.util.Log
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
import com.dev_talk.common.structures.ProfessionDto
import com.dev_talk.databinding.FragmentPersonalChatsBinding
import com.dev_talk.dto.ChatDto
import com.dev_talk.main.structures.Chat
import com.dev_talk.main.structures.Profession
import com.dev_talk.utils.DATABASE_URL
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalChatsBinding.inflate(inflater)
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setUpChats()
            //val data = getProfessions()

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

    private fun setUpData(data: List<Profession>) {
        setUpViewPager2(
            viewPager = binding.chatsWithCategory,
            tabLayout = binding.professions,
            data = data
        )
        for ((index, currentProfession) in data.withIndex()) {
            if (index < 3) {
                binding.professions.getTabAt(index)?.text = currentProfession.profession
            }
        }
    }

    private fun addTabs() {
        db.child("users").child(auth.currentUser?.uid!!).child("user_info").get()
            .addOnSuccessListener {
                it.children.forEach {
                    val tabName : String = it.key!!
                    Log.d("profession", tabName)

                    binding.professions.addTab(binding.professions.newTab()
                        .setText(tabName))
                }
            }
            .addOnFailureListener {
                Log.d("chats", it.message.toString())
            }
    }

    private fun setUpChats() {
        val chats = db.child("chats")
        val chatsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //addTabs()
                Log.d("chats count", snapshot.childrenCount.toString())
                val chatProfessions: MutableList<Profession> = mutableListOf()
                snapshot.children.forEach {
                    val professions = getProfessions()
                    if (it.child("participants").child(auth.currentUser?.uid.toString()).exists()) {
                        val id: String = it.key!!
                        val chat = it.getValue<ChatDto>()
                        Log.d("id", chat!!.name + chat.tag)
                        val prof = professions.firstOrNull { p ->
                            p.tags.find { t -> t.name == chat.tag } != null
                        }
                        if (prof != null) {
                            val alreadyExistedProfession =
                                chatProfessions.find { p -> p.profession == prof.name }
                            val newChat = Chat(
                                R.drawable.default_avatar_chat,
                                chat.name!!,
                                "last message"
                            )
                            if (alreadyExistedProfession != null) {
                                alreadyExistedProfession.chats.add(newChat)
                            } else {
                                chatProfessions.add(Profession(prof.name, mutableListOf(newChat)))
                            }

                        }

                    }
                }
                setUpData(chatProfessions.toList())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        chats.addValueEventListener(chatsListener)
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
        viewPager.apply {
            val tabs = getProfessionsNames()
            adapter = PersonalChatsAdapterViewPager(
                activity = requireActivity(),
                itemCount = tabLayout.tabCount,
                data = data,
                tabNames = tabs
            )
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }
    }
}
