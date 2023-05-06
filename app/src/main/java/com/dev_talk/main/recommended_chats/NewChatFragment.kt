package com.dev_talk.main.recommended_chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.databinding.FragmentNewChatBinding
import com.dev_talk.utils.getProfessions

class NewChatFragment : Fragment() {
    private lateinit var binding: FragmentNewChatBinding

    private lateinit var chosenProfessionName: String
    private lateinit var chosenTagName: String
    private lateinit var chatName: String

    private var isNameValid = false
    private var isTagValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeNavigationCallBack()
        updateDoneButtonStatus()
    }

    private fun initListeners() {
        binding.chatName.doOnTextChanged { text, start, before, count ->
            chatName = text.toString()
            isNameValid = chatName.isNotEmpty()
            updateDoneButtonStatus()
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.chooseTagButton.setOnClickListener {
            findNavController().navigate(R.id.action_newChatFragment_to_tagChoiceFragment)
        }

        binding.doneButton.setOnClickListener {
            val chosenTag = getProfessions()
                .first { it.name == chosenProfessionName }.tags
                .first { it.name == chosenTagName }

            println("CHAT NAME: " + chatName)
            println("TAG: " + chosenTag)

            findNavController().popBackStack()
        }
    }

    private fun updateDoneButtonStatus() {
        binding.doneButton.isEnabled = isNameValid && isTagValid
    }

    private fun handleTagChoice(newTest: String) {
        // TODO to strings
        val buttonText = "Chat tag: $newTest"
        binding.chooseTagButton.text = buttonText
        isTagValid = true
        updateDoneButtonStatus()
    }

    private fun observeNavigationCallBack() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("profession")
            ?.observe(viewLifecycleOwner) {
                chosenProfessionName = it
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("tag")
            ?.observe(viewLifecycleOwner) {
                chosenTagName = it
                handleTagChoice(chosenTagName)
            }
    }
}
