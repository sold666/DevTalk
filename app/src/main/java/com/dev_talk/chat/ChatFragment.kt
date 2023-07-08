package com.dev_talk.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        val settingsButton = binding.root.findViewById<View>(R.id.chat_settings)
        val toolbar = binding.root.findViewById<Toolbar>(R.id.chat_app_bar)

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_chatFragment_to_chatSettingsFragment)
        }

        toolbar.setNavigationOnClickListener {
            activity?.finish()
        }
    }
}
