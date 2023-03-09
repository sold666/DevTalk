package com.dev_talk.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.main.databinding.FragmentPersonalChatListBinding
import com.dev_talk.main.recycler_view_adapters.PersonalChatsAdapter
import com.dev_talk.main.structures.Profession

private const val PROFESSION_KEY = "Current profession"

class PersonalChatListFragment : Fragment() {
    private var binding: FragmentPersonalChatListBinding? = null
    private val _binding: FragmentPersonalChatListBinding
        get() = binding!!

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(_binding.listWithMyChats) {
            val data = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(PROFESSION_KEY, Profession::class.java)!!
            } else {
                arguments?.getParcelable(PROFESSION_KEY)!!
            }
            adapter = PersonalChatsAdapter(data.chats)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalChatListBinding.inflate(inflater)
        return _binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(profession: Profession) =
            PersonalChatListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PROFESSION_KEY, profession)
                }
            }
    }
}