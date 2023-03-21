package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.LIST_SELECTED_TAGS_KEY
import com.dev_talk.R
import com.dev_talk.databinding.TagFragmentBinding
import com.dev_talk.structures.Profession

class TagFragment : Fragment() {

    private lateinit var binding: TagFragmentBinding
    private lateinit var tagAdapter: TagAdapter
    private lateinit var tags: ArrayList<String>
    private lateinit var selectedProfessions: ArrayList<Profession>
    private var selectedTags: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TagFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = binding.nextButton
        val buttonBack = binding.backButton

        buttonNext.setOnClickListener {
            selectedTags = tagAdapter.getSelectedTags()
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
            findNavController().navigate(R.id.action_tagsFragment_to_resultFragment, bundle)
        }

        buttonBack.setOnClickListener {
            findNavController().popBackStack()
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
            tags = ArrayList(selectedProfessions.flatMap(Profession::tags))
            tagAdapter = TagAdapter(tags, selectedTags, buttonNext)
            tagList.adapter = tagAdapter
            tagList.layoutManager = LinearLayoutManager(tagList.context)
        }
        buttonNext.isEnabled = tagAdapter.getSelectedTags().isNotEmpty()
    }
}
