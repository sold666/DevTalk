package com.dev_talk.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.developers_messenger.R
import com.example.developers_messenger.databinding.ChatSettingsFragmentBinding

class ChatSettingsFragment : Fragment() {

    private lateinit var binding: ChatSettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChatSettingsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chatParticipantAdapter = ChatParticipantAdapter(getParticipantList())
        with(binding) {
            participantList.adapter = chatParticipantAdapter
            participantList.layoutManager = LinearLayoutManager(participantList.context)
            amountChatParticipants.text = String.format(
                resources.getString(R.string.members),
                chatParticipantAdapter.itemCount
            )
        }
        setListeners()
    }

    private fun setListeners() {
        binding.chatSettingsReturnButton.setOnClickListener {
            findNavController().navigate(R.id.action_chatSettingsFragment_to_chatFragment)
        }
    }

    private fun getParticipantList(): List<ChatParticipant> {
        return arrayListOf(
            ChatParticipant(R.drawable.ic_launcher_foreground, "Gena", "online"),
            ChatParticipant(R.drawable.ic_launcher_foreground, "Alena", "online"),
            ChatParticipant(R.drawable.ic_launcher_foreground, "Alina", "offline")
        )
    }
}
