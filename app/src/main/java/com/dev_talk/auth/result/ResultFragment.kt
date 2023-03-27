package com.dev_talk.auth.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.databinding.ResultFragmentBinding
import com.dev_talk.structures.Profession
import com.dev_talk.structures.Tag
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.LIST_SELECTED_TAGS_KEY

class ResultFragment : Fragment() {

    private lateinit var binding: ResultFragmentBinding
    private lateinit var selectedTags: List<Tag>
    private lateinit var selectedProfessions: List<Profession>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResultFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        with(binding) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                selectedTags = arguments?.getParcelableArrayList(
                    LIST_SELECTED_TAGS_KEY,
                    Tag::class.java
                )!!
                selectedProfessions = arguments?.getParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    Profession::class.java
                )!!
            } else {
                selectedTags = arguments?.getParcelableArrayList(LIST_SELECTED_TAGS_KEY)!!
                selectedProfessions =
                    arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
            }
            val listData = generateMapFromArrays()
            val titleList = selectedProfessions.map { it.name }
            val resultAdapter = ResultAdapter(resultList.context, listData, titleList)
            resultList.setAdapter(resultAdapter)
            for (i in listData.keys.indices) {
                resultList.expandGroup(i)
            }
        }
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener { findNavController().popBackStack() }

        binding.nextButton.setOnClickListener {

        }
    }

    private fun generateMapFromArrays(): Map<String, List<String>> {
        val expandableListMap = HashMap<String, List<String>>()
        for (profession in selectedProfessions) {
            val tagsNamesList = profession.tags.filter { selectedTags.contains(it) }.map { it.name }
            expandableListMap[profession.name] = tagsNamesList
        }
        return expandableListMap
    }
}
