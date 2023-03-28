package com.dev_talk.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.developers_messenger.R
import com.example.developers_messenger.databinding.ChatFragmentBinding

class ChatFragment : Fragment() {

    private lateinit var binding: ChatFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChatFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        val settingsButton = binding.root.findViewById<View>(R.id.chatSettings)
        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_chatFragment_to_chatSettingsFragment)
        }
    }
}
