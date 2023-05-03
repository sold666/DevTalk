package com.dev_talk.main.profile

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.databinding.FragmentProfileBinding
import com.dev_talk.main.structures.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val DEFAULT_LIST_PROFESSIONS_KEY = "professions"

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var data: List<ProfileData>
    private var isNightModeOn: Boolean = false
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            data = getProfileData()
            setUpRecyclerView(recyclerView = myChats)
            setUpLinks(socialNetwork)

            isNightModeOn =
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            switchThemeButton.setImageResource(if (isNightModeOn) R.drawable.moon else R.drawable.sun)

            switchThemeButton.setOnClickListener {
                val newMode = if (isNightModeOn) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                AppCompatDelegate.setDefaultNightMode(newMode)
                switchThemeButton.setImageResource(if (isNightModeOn) R.drawable.sun else R.drawable.moon)
                isNightModeOn = !isNightModeOn
                val toastText = if (isNightModeOn) "Night mode ON" else "Night mode OFF"
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            }
            listenMenuButtons()
        }
    }

    private fun listenMenuButtons() {
        val toolbar = binding.profileAppBar

        if (toolbar.menu.size() == 0) {
            toolbar.inflateMenu(R.menu.profile_app_bar)
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit -> {
                    // todo обработчик для R.id.edit
                    true
                }

                R.id.log_out -> {
                    auth.signOut()
                    findNavController().navigate(R.id.action_profile_fragment_container_to_authActivity)
                    true
                }

                else -> {
                    false
                }
            }
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

    private fun getLinks(): ArrayList<Link> {
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
                    R.drawable.ic_person,
                    "C++"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Java"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "C"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Kotlin"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "F"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Ruby"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Go"
                )
            ),
            Header("Profession №2"),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Css"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Html"
                )
            ),
            Header("Profession №3"),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Selenide"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Selenium"
                )
            ),
            Item(
                Chat(
                    R.drawable.ic_person,
                    "Java"
                )
            )
        )
    }
}
