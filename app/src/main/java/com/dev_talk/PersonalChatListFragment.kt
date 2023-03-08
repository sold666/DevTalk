package com.dev_talk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.databinding.FragmentPersonalChatListBinding
import com.dev_talk.recycler_view_adapters.PersonalChatsAdapter
import com.dev_talk.structures.Profession

class PersonalChatListFragment(private val profession: Profession) : Fragment() {
    private var binding: FragmentPersonalChatListBinding? = null
    private val _binding: FragmentPersonalChatListBinding
        get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(_binding.listWithMyChats) {
            adapter = PersonalChatsAdapter(profession.chats)
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
}
