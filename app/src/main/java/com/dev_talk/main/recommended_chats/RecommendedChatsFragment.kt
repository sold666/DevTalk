package com.dev_talk.main.recommended_chats


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.main.R
import com.dev_talk.main.databinding.FragmentRecommendedChatsBinding
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
            dataRV =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelableArrayList(
                        RECOMMENDED_LIST_PROFESSIONS_KEY,
                        Chat::class.java
                    )!!
                } else {
                    arguments?.getParcelableArrayList(RECOMMENDED_LIST_PROFESSIONS_KEY)!!
                }
            adapterRV = RecommendedChatsAdapter(dataRV)
            setUpRecyclerView(recommendedChats)
            setUpSearchView()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(chats: ArrayList<Chat>) =
            RecommendedChatsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(RECOMMENDED_LIST_PROFESSIONS_KEY, chats)
                }
            }
    }

    private fun getRecyclerViewDivider(context: Context): DividerItemDecoration {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.divider
            )!!
        )
        return decoration
    }

    private fun setUpSearchView() {
        val searchView = binding.searchBar.menu.findItem(R.id.menu_search)?.actionView as SearchView
        with(searchView) {
            maxWidth = Integer.MAX_VALUE;
            queryHint = getString(R.string.default_query_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    binding.searchBar.menu.findItem(R.id.menu_search)?.collapseActionView()
                    adapterRV.filter.filter(query)
                    if (binding.recommendedChats.size == 0) {
                        adapterRV.filter.filter("")
                    }
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    adapterRV.filter.filter(query)
                    return true
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
