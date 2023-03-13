package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.*
import com.dev_talk.databinding.TagFragmentBinding
import com.dev_talk.structures.Profession
import androidx.navigation.fragment.findNavController
import com.dev_talk.R

class TagFragment : Fragment() {

    private lateinit var binding: TagFragmentBinding
    private lateinit var tagAdapter: TagAdapter
    private lateinit var tags: ArrayList<String>
    private lateinit var selectedProfessions: ArrayList<Profession>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TagFragmentBinding.inflate(inflater)
        binding.nextButton.setOnClickListener { onClickNext() }
        binding.backButton.setOnClickListener { onClickBack() }
        return binding.root
    }

    private fun onClickNext() {
        val selectedTags = tagAdapter.getSelectedTags()
        if (selectedTags.isEmpty()) {
            return
        }
        val resultFragment = ResultFragment()
        val bundle = Bundle().apply {
            putStringArrayList(
                LIST_TAGS_KEY,
                tags
            )
            putStringArrayList(
                LIST_SELECTED_TAGS_KEY,
                selectedTags
            )
            putParcelableArrayList(
                LIST_SELECTED_PROFESSIONS_KEY,
                selectedProfessions
            )
        }
        resultFragment.arguments = bundle

        requireFragmentManager()
            .beginTransaction()
            .replace(R.id.container, resultFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun onClickBack() {
        val professionFragment = ProfessionFragment()
        val bundle = Bundle().apply {
            putParcelableArrayList(
                LIST_PROFESSIONS_KEY,
                getProfessions()
            )
            putParcelableArrayList(
                LIST_SELECTED_PROFESSIONS_KEY,
                selectedProfessions
            )
        }
        professionFragment.arguments = bundle

        requireFragmentManager()
            .beginTransaction()
            .replace(R.id.container, professionFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = binding.nextButton
        val buttonBack = binding.backButton

        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_tagsFragment_to_resultFragment)
        }

        buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_tagsFragment_to_professionFragment)
        }
        with(binding) {
            selectedProfessions =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelableArrayList(
                        LIST_SELECTED_PROFESSIONS_KEY,
                        Profession::class.java
                    )!!
                } else {
                    arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
                }
            val selectedTags =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getStringArrayList(LIST_SELECTED_TAGS_KEY)!!
                } else {
                    arguments?.getStringArrayList(LIST_SELECTED_TAGS_KEY)!!
                }
            tags = ArrayList(selectedProfessions.flatMap(Profession::tags))
            tagAdapter = TagAdapter(tags, selectedTags)
            tagList.adapter = tagAdapter
            tagList.layoutManager = LinearLayoutManager(tagList.context)
        }
    }
}
