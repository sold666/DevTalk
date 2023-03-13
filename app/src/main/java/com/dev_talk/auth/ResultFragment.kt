package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dev_talk.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.LIST_SELECTED_TAGS_KEY
import com.dev_talk.LIST_TAGS_KEY
import com.dev_talk.R
import com.dev_talk.databinding.ResultFragmentBinding
import com.dev_talk.structures.Profession
import androidx.navigation.fragment.findNavController

class ResultFragment : Fragment() {

    private lateinit var binding: ResultFragmentBinding
    private lateinit var tags: ArrayList<String>
    private lateinit var selectedTags: ArrayList<String>
    private lateinit var selectedProfessions: ArrayList<Profession>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResultFragmentBinding.inflate(inflater)
        binding.backButton.setOnClickListener { onClickBack() }
        return binding.root
    }

    private fun onClickBack() {
        val tagFragment = TagFragment()
        val bundle = Bundle().apply {
            putStringArrayList(
                LIST_SELECTED_TAGS_KEY,
                selectedTags
            )
            putParcelableArrayList(
                LIST_SELECTED_PROFESSIONS_KEY,
                selectedProfessions
            )
        }
        tagFragment.arguments = bundle

        requireFragmentManager()
            .beginTransaction()
            .replace(R.id.container, tagFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val buttonNext = binding.nextButton
        val buttonBack = binding.backButton

        buttonNext.setOnClickListener {

        }

        buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_tagsFragment)
        }
        with(binding) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                tags = arguments?.getStringArrayList(LIST_TAGS_KEY)!!
                selectedTags = arguments?.getStringArrayList(LIST_SELECTED_TAGS_KEY)!!
                selectedProfessions = arguments?.getParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    Profession::class.java
                )!!
            } else {
                tags = arguments?.getStringArrayList(LIST_TAGS_KEY)!!
                selectedTags = arguments?.getStringArrayList(LIST_SELECTED_TAGS_KEY)!!
                selectedProfessions =
                    arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
            }
            val listData = generateMapFromArrays()
            val resultAdapter = ResultAdapter(resultList.context, listData)
            resultList.setAdapter(resultAdapter)
            for (i in listData.keys.indices) {
                resultList.expandGroup(i)
            }
        }
    }

    private fun generateMapFromArrays(): HashMap<String, List<String>> {
        val expandableListMap = HashMap<String, List<String>>()
        for (profession in selectedProfessions) {
            val tagsNamesList = selectedTags.filter { profession.tags.contains(it) }
            if (tagsNamesList.isNotEmpty()) {
                expandableListMap[profession.name] = tagsNamesList
            }
        }
        return expandableListMap
    }
}
