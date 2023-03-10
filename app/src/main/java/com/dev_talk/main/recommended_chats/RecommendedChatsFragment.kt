package com.dev_talk.main.recommended_chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.main.databinding.FragmentRecommendedChatsBinding
import com.dev_talk.main.structures.Chat

private const val RECOMMENDED_LIST_PROFESSIONS_KEY = "recommendations"

class RecommendedChatsFragment : Fragment() {
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
            val data = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(RECOMMENDED_LIST_PROFESSIONS_KEY, Chat::class.java)!!
            } else {
                arguments?.getParcelableArrayList(RECOMMENDED_LIST_PROFESSIONS_KEY)!!
            }
            with (recommendedChats) {
                adapter = RecommendedChatsAdapter(data)
                layoutManager = LinearLayoutManager(context)
            }
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
}
