package com.dev_talk.main.recommended_chats


import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.main.R
import com.dev_talk.main.databinding.FragmentRecommendedChatsBinding
import com.dev_talk.main.structures.Chat


private const val RECOMMENDED_LIST_PROFESSIONS_KEY = "recommendations"

class RecommendedChatsFragment : Fragment(), MenuProvider {
    private lateinit var binding: FragmentRecommendedChatsBinding

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
            val data =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelableArrayList(
                        RECOMMENDED_LIST_PROFESSIONS_KEY,
                        Chat::class.java
                    )!!
                } else {
                    arguments?.getParcelableArrayList(RECOMMENDED_LIST_PROFESSIONS_KEY)!!
                }
            val adapterRV = RecommendedChatsAdapter(data)
            recommendedChats.apply {
                adapter = adapterRV
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(getRecyclerViewDivider(context))
            }
            searchBar.setOnClickListener {
                searchBar.onActionViewExpanded()
            }
            searchBar.isSubmitButtonEnabled = true
            searchBar.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        searchBar.clearFocus()
                        adapterRV.filter.filter(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        adapterRV.filter.filter(newText)
                        return true
                    }
                }
            )
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.recommendations_search_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        binding.searchBar.setQuery("", false);
        super.onPause()
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

}
