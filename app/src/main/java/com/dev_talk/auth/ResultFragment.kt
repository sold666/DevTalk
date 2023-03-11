package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dev_talk.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.LIST_SELECTED_TAGS_KEY
import com.dev_talk.LIST_TAGS_KEY
import com.dev_talk.main.R
import com.dev_talk.main.databinding.ResultFragmentBinding
import com.dev_talk.structures.Profession

class ResultFragment : Fragment() {

    private var binding: ResultFragmentBinding? = null
    private val _binding get() = binding!!
    private lateinit var tags: ArrayList<String>
    private lateinit var selectedTags: ArrayList<String>
    private lateinit var selectedProfessions: ArrayList<Profession>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResultFragmentBinding.inflate(inflater)

        binding!!.nextButton.setOnClickListener { onClickNext() }
        binding!!.backButton.setOnClickListener { onClickBack() }

        return _binding.root
    }

    private fun onClickNext() {

    }

    private fun onClickBack() {
        val tagFragment = TagFragment()
        val bundle = Bundle().apply {
            putStringArrayList(
                LIST_SELECTED_TAGS_KEY,
                selectedTags
            )
        }
        bundle.apply {
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
        val context = this
        with(_binding) {
            tags = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getStringArrayList(LIST_TAGS_KEY)!!
            } else {
                arguments?.getStringArrayList(LIST_TAGS_KEY)!!
            }
            selectedTags = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getStringArrayList(LIST_SELECTED_TAGS_KEY)!!
            } else {
                arguments?.getStringArrayList(LIST_SELECTED_TAGS_KEY)!!
            }
            selectedProfessions = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY, Profession::class.java)!!
            } else {
                arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
            }
            val listData = generateMapFromArrays()
            val resultAdapter = ResultAdapter(tagResultExpandableList.context, listData)
            tagResultExpandableList.setAdapter(resultAdapter)
            for (i in listData.keys.indices) {
                tagResultExpandableList.expandGroup(i)
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

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
