package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dev_talk.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.LIST_SELECTED_TAGS_KEY
import com.dev_talk.R
import com.dev_talk.databinding.ResultFragmentBinding
import com.dev_talk.structures.Profession
import androidx.navigation.fragment.findNavController

class ResultFragment : Fragment() {

    private lateinit var binding: ResultFragmentBinding
    private lateinit var selectedTags: ArrayList<String>
    private lateinit var selectedProfessions: ArrayList<Profession>

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

        val buttonNext = binding.nextButton
        val buttonBack = binding.backButton

        buttonNext.setOnClickListener {

        }

        buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        with(binding) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                selectedTags = arguments?.getStringArrayList(LIST_SELECTED_TAGS_KEY)!!
                selectedProfessions = arguments?.getParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    Profession::class.java
                )!!
            } else {
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
            expandableListMap[profession.name] = tagsNamesList
        }
        return expandableListMap
    }
}

// todo убрать галочку на пустых профессиях при развертывании списка
