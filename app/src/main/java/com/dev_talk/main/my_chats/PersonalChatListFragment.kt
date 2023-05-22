package com.dev_talk.main.my_chats

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

        val chatDataObserver = Observer<List<Profession>> { newChatData ->
            // Обновление данных в RecyclerView
            for (i in newChatData) {
                Log.d("rww", i.profession.toString() + i.chats.size.toString())
            }

            Log.d("prof", data.profession)
            Log.d("data", newChatData.toString())

            var chats = newChatData.findLast { p -> p.profession == data.profession }?.chats
            if (chats != null) {
                chats = chats.filter { ch -> !data.chats.map { c -> c.id }.contains(ch.id)
                } as MutableList<Chat>
                data.chats.addAll(0, chats)
                adapterRV.notifyItemRangeInserted(0, chats.size)
            }
        }
        ChatDataRepository.chatData.observe(viewLifecycleOwner, chatDataObserver)
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
