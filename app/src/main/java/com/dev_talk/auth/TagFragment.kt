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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = binding.nextButton
        val buttonBack = binding.backButton

        buttonNext.setOnClickListener {
            val bundle = Bundle().apply {
                putStringArrayList(
                    LIST_SELECTED_TAGS_KEY,
                    tagAdapter.getSelectedTags()
                )
                putParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    selectedProfessions
                )
            }
            findNavController().navigate(R.id.action_tagsFragment_to_resultFragment, bundle)
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
            tags = ArrayList(selectedProfessions.flatMap(Profession::tags))
            tagAdapter = TagAdapter(tags, arrayListOf()) //todo
            tagList.adapter = tagAdapter
            tagList.layoutManager = LinearLayoutManager(tagList.context)
        }
    }
}
