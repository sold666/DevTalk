package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.*
import com.dev_talk.main.*
import com.dev_talk.main.databinding.TagFragmentBinding
import com.dev_talk.structures.Profession

class TagFragment : Fragment() {

    private var binding: TagFragmentBinding? = null
    private val _binding get() = binding!!
    private lateinit var tagAdapter: TagAdapter
    private lateinit var tags: ArrayList<String>
    private lateinit var selectedProfessions: ArrayList<Profession>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TagFragmentBinding.inflate(inflater)

        binding!!.nextButton.setOnClickListener { onClickNext() }
        binding!!.backButton.setOnClickListener { onClickBack() }

        return _binding.root
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
        }
        bundle.apply {
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
        }
        bundle.apply {
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

        with(_binding) {
            selectedProfessions = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY, Profession::class.java)!!
            } else {
                arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
            }
            val selectedTags = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
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

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
