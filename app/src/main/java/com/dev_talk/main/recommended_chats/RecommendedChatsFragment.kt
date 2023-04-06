package com.dev_talk.main.recommended_chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.databinding.FragmentRecommendedChatsBinding
import com.dev_talk.main.common.Divider.Companion.getRecyclerViewDivider
import com.dev_talk.main.structures.Chat

private const val RECOMMENDED_LIST_PROFESSIONS_KEY = "recommendations"

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
                R.drawable.ic_my_chats_navigation,
                "Python"
            ),
            Chat(
                R.drawable.ic_my_chats_navigation,
                "Java Script"
            ),
            Chat(
                R.drawable.ic_my_chats_navigation,
                "Assembler"
            )
        )
    }

    private fun setUpSearchView() {
        val searchView = binding.searchBar.menu.findItem(R.id.menu_search)?.actionView as SearchView
        val searchBarMenu = binding.searchBar.menu
        val searchViewIcon: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon)
        searchViewIcon.layoutParams = LinearLayout.LayoutParams(0, 0)
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
                    adapterRV.filter.filter(query)
                }
            })
        }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            adapter = adapterRV
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(getRecyclerViewDivider(context))
        }
    }
}
