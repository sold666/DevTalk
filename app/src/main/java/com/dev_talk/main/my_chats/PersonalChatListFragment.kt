package com.dev_talk.main.my_chats

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.chat.ChatActivity
import com.dev_talk.databinding.FragmentPersonalChatListBinding
import com.dev_talk.main.common.Divider.Companion.getRecyclerViewDivider
import com.dev_talk.main.structures.Chat
import com.dev_talk.main.structures.Profession

private const val PROFESSION_KEY = "Current profession"

class PersonalChatListFragment : Fragment() {
    private lateinit var binding: FragmentPersonalChatListBinding
    private lateinit var onChatClickListener: (chat: Chat, adapterPosition: Int) -> Unit
    private lateinit var adapterRV: PersonalChatsAdapter

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
        val data =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(PROFESSION_KEY, Profession::class.java)!!
            } else {
                arguments?.getParcelable(PROFESSION_KEY)!!
            }
        adapterRV = PersonalChatsAdapter(data.chats, onChatClickListener)
        adapterRV.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                if (adapterRV.itemCount == 0) {
                    binding.noChatsDetected.visibility = View.VISIBLE
                } else {
                    binding.noChatsDetected.visibility = View.GONE
                }
            }
        })

        with(binding.listWithMyChats) {
            adapter = adapterRV
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
