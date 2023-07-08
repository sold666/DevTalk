package com.dev_talk.main.recommended_chats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.databinding.FragmentRecommendedChatsBinding
import com.dev_talk.dto.ChatDto
import com.dev_talk.main.common.Divider.Companion.getRecyclerViewDivider
import com.dev_talk.main.profile.ProfileCache
import com.dev_talk.main.structures.Chat
import com.dev_talk.main.structures.Item
import com.dev_talk.utils.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class RecommendedChatsFragment : Fragment() {
    private lateinit var db: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentRecommendedChatsBinding
    private lateinit var adapterRV: RecommendedChatsAdapter
    private lateinit var dataRV: ArrayList<Chat>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        auth = Firebase.auth
        binding = FragmentRecommendedChatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        with(binding) {
            dataRV = arrayListOf()
            adapterRV = RecommendedChatsAdapter(dataRV)
            setUpRecyclerView(recommendedChats)
            setUpSearchView()
        }
        getRecommendedChatsFromDatabase()
    }


    private fun getRecommendedChatsFromDatabase() {
        val ref = db.child("chats")
        val recommendedChatListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (it in snapshot.children) {
                    if (!it.child("participants").child(auth.currentUser?.uid.toString()).exists()) {
                        val chatDto = it.getValue<ChatDto>()
                        val needTag =
                            ProfileCache.profileData.findLast { pd ->
                                pd is Item && pd.userTags.tag == chatDto?.tag
                            }
                        if (needTag != null) {
                            val chat = Chat(
                                it.key!!,
                                R.drawable.default_avatar_chat,
                                chatDto?.name!!,
                                "last message"
                            )
                            dataRV.add(chat)
                        }
                    }
                }
                adapterRV.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", error.message)
            }
        }
        ref.addValueEventListener(recommendedChatListener)
    }

    private fun setUpSearchView() {
        val searchView = binding.searchBar.menu.findItem(R.id.menu_search)?.actionView as SearchView
        val searchBarMenu = binding.searchBar.menu
        val searchViewIcon: ImageView =
            searchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
        searchViewIcon.layoutParams = LinearLayout.LayoutParams(0, 0)
        searchView.apply {
            isIconified = false
            maxWidth = Integer.MAX_VALUE;
            queryHint = getString(R.string.default_query_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    searchBarMenu.findItem(R.id.menu_search)?.collapseActionView()
                    filterData("")
                    binding.noChatsDetected.visibility = View.GONE
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    filterData(query)
                    return true
                }

                private fun filterData(query: String?) {
                    adapterRV.filter.filter(query)
                }
            })
        }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        adapterRV.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (adapterRV.itemCount == 0) {
                    binding.noChatsDetected.visibility = View.VISIBLE
                } else {
                    binding.noChatsDetected.visibility = View.GONE
                }
            }
        })
        recyclerView.apply {
            adapter = adapterRV
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(getRecyclerViewDivider(context))
        }
    }

    private fun initListeners() {
        binding.addChatButton.setOnClickListener {
            findNavController().navigate(R.id.action_recommendedChatsFragment_to_newChatFragment)
        }
    }
}
