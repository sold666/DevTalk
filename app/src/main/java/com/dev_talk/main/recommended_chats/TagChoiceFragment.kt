package com.dev_talk.main.recommended_chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.common.structures.ProfessionDto
import com.dev_talk.databinding.FragmentNewChatTagChoiceBinding
import com.dev_talk.utils.getProfessions

class TagChoiceFragment : Fragment() {
    private lateinit var binding: FragmentNewChatTagChoiceBinding
    private lateinit var tagChoiceAdapter: TagChoiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewChatTagChoiceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        with(binding) {
            val titleList = getProfessions().map { it.name  }.sorted()
            val listData = generateMapFromArrays(getProfessions())
            tagChoiceAdapter = TagChoiceAdapter(choiceList.context, listData, titleList)
            choiceList.setAdapter(tagChoiceAdapter)
            for (i in listData.keys.indices) {
                choiceList.expandGroup(i)
            }
        }
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.choiceList.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            findNavController().previousBackStackEntry?.savedStateHandle
                ?.set("profession", tagChoiceAdapter.getGroup(groupPosition))
            findNavController().previousBackStackEntry?.savedStateHandle
                ?.set("tag", tagChoiceAdapter.getChild(groupPosition, childPosition))
            findNavController().popBackStack()
            true
        }
    }

    private fun generateMapFromArrays(professions: List<ProfessionDto>): Map<String, List<String>> {
        val expandableListMap = HashMap<String, List<String>>()
        for (profession in professions) {
            val tagsNamesList = profession.tags.map { it.name }
            expandableListMap[profession.name] = tagsNamesList
        }
        return expandableListMap
    }
}
