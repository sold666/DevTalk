package com.dev_talk.main.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.databinding.FragmentProfileBinding
import com.dev_talk.main.structures.Chat
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.ProfileData

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

        }
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
