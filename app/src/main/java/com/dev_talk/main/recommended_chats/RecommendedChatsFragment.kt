package com.dev_talk.main.recommended_chats

import android.os.Bundle
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
import com.dev_talk.main.common.Divider.Companion.getRecyclerViewDivider
import com.dev_talk.main.structures.Chat

class RecommendedChatsFragment : Fragment() {
    private lateinit var binding: FragmentRecommendedChatsBinding
    private lateinit var adapterRV: RecommendedChatsAdapter
    private lateinit var dataRV: List<Chat>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendedChatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        with(binding) {
            dataRV = getRecommendedChats()
            adapterRV = RecommendedChatsAdapter(dataRV)
            setUpRecyclerView(recommendedChats)
            setUpSearchView()
        }
    }

    private fun getRecommendedChats(): ArrayList<Chat> {
        return arrayListOf(
            Chat(
                R.drawable.default_avatar_chat,
                "Python",
                memberCount = "Members count: 20k"
            ),
            Chat(
                R.drawable.default_avatar_chat,
                "Java Script",
                memberCount = "Members count: 22k"
            ),
            Chat(
                R.drawable.default_avatar_chat,
                "Lua",
                memberCount = "Members count: 2k"
            ),
            Chat(
                R.drawable.default_avatar_chat,
                "Lisp",
                memberCount = "Members count: 5k"
            ),
            Chat(
                R.drawable.default_avatar_chat,
                "Fortran",
                memberCount = "Members count: 20"
            ),
            Chat(
                R.drawable.default_avatar_chat,
                "MatLab",
                memberCount = "Members count: 5.5k"
            ),
            Chat(
                R.drawable.default_avatar_chat,
                "F",
                memberCount = "Members count: 50k"
            ),
            Chat(
                R.drawable.default_avatar_chat,
                "C++",
                memberCount = "Members count: 50k"
            ),
            Chat(
                R.drawable.default_avatar_chat,
                "Assembler",
                memberCount = "Members count: 2"
            )
        )
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
