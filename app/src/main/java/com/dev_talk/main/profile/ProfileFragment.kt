package com.dev_talk.main.profile

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.databinding.FragmentProfileBinding
import com.dev_talk.main.structures.*

private const val DEFAULT_LIST_PROFESSIONS_KEY = "professions"

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var data: List<ProfileData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            data = getProfileData()
            setUpRecyclerView(recyclerView = myChats)
            setUpLinks(socialNetwork)
//            (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
//                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                    menuInflater.inflate(R.menu.profile_app_bar, menu)
//                }
//
//                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//            })
        }
    }

    private fun setUpLinks(recyclerView: RecyclerView) {
        val manager = LinearLayoutManager(context)
        manager.orientation = RecyclerView.HORIZONTAL
        recyclerView.apply {
            layoutManager = manager
            adapter = ProfileLinkAdapter(getLinks())
            setHasFixedSize(true)
        }
    }

    private fun getLinks() : ArrayList<Link> {
        return arrayListOf(
            Link(R.drawable.ic_person),
            Link(R.drawable.ic_person)
        )
    }

    private fun setUpRecyclerView(
        recyclerView: RecyclerView
    ) {
        val manager = GridLayoutManager(context, 2)
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (data[position]) {
                    is Header -> 2
                    is Item -> 1
                }
            }
        }

        manager.orientation = RecyclerView.VERTICAL
        recyclerView.apply {
            layoutManager = manager
            adapter = ProfileChatsAdapter(data)
            setHasFixedSize(true)
        }
    }

    private fun getProfileData(): ArrayList<ProfileData> {
        return arrayListOf(
            Header("Profession №1"),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "C++"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "Java"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "C"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "Kotlin"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "F"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "Ruby"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "Go"
                )
            ),
            Header("Profession №2"),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "Css"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "Html"
                )
            ),
            Header("Profession №3"),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "Selenide"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "Selenium"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_my_chats_navigation,
                    "Java"
                )
            )
        )
    }

}
