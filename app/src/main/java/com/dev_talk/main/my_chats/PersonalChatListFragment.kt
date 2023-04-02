package com.dev_talk.main.my_chats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.R
import com.dev_talk.auth.structures.Tag
import com.dev_talk.chat.ChatActivity
import com.dev_talk.databinding.FragmentPersonalChatListBinding
import com.dev_talk.main.MainActivity
import com.dev_talk.main.common.Divider.Companion.getRecyclerViewDivider
import com.dev_talk.main.structures.Chat
import com.dev_talk.main.structures.Profession
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.LIST_SELECTED_TAGS_KEY

private const val PROFESSION_KEY = "Current profession"

class PersonalChatListFragment : Fragment() {
    private lateinit var binding: FragmentPersonalChatListBinding
    private lateinit var chats: List<Chat>
    private lateinit var onChatClickListener: (chat: Chat, adapterPosition: Int) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalChatListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        with(binding.listWithMyChats) {
            val data =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelable(PROFESSION_KEY, Profession::class.java)!!
                } else {
                    arguments?.getParcelable(PROFESSION_KEY)!!
                }
            adapter = PersonalChatsAdapter(data.chats, onChatClickListener)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(getRecyclerViewDivider(context))
        }
    }

    private fun initListeners() {
        onChatClickListener = { chat, position ->
            val intent = Intent(context, ChatActivity::class.java)
            context?.startActivity(intent)
        }
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
