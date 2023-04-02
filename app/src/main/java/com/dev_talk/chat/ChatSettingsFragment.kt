package com.dev_talk.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.R
import com.dev_talk.chat.structures.ChatParticipant
import com.dev_talk.databinding.FragmentChatSettingsBinding

class ChatSettingsFragment : Fragment() {

    private lateinit var binding: FragmentChatSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatSettingsBinding.inflate(inflater)
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
            findNavController().popBackStack()
        }
    }

    private fun getParticipantList(): List<ChatParticipant> {
        return arrayListOf(
            ChatParticipant(R.drawable.ic_person, "Gena", "online"),
            ChatParticipant(R.drawable.ic_person, "Alena", "online"),
            ChatParticipant(R.drawable.ic_person, "Alina", "offline")
        )
    }
}
