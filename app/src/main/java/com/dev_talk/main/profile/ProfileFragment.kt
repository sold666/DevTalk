package com.dev_talk.main.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.main.databinding.FragmentProfileBinding
import com.dev_talk.main.structures.Profession


private const val DEFAULT_LIST_PROFESSIONS_KEY = "professions"

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

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
            val professions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(
                    DEFAULT_LIST_PROFESSIONS_KEY,
                    Profession::class.java
                )!!
            } else {
                arguments?.getParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY)!!
            }
            setUpRecyclerView(recyclerView = myChats, professions = professions)

        }
    }

    private fun setUpRecyclerView(
        recyclerView: RecyclerView,
        professions: ArrayList<Profession>
    ) {
        val manager = LinearLayoutManager(context)
        manager.orientation = RecyclerView.VERTICAL
        recyclerView.apply {
            adapter = ProfileChatsAdapter(professions)
            setHasFixedSize(true)
            layoutManager = manager
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(professions: ArrayList<Profession>) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY, professions)
                }
            }
    }
}