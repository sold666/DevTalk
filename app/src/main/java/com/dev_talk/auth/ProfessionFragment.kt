package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.LIST_SELECTED_TAGS_KEY
import com.dev_talk.getProfessions
import com.dev_talk.R
import com.dev_talk.databinding.ProfessionFragmentBinding
import com.dev_talk.structures.Profession

class ProfessionFragment : Fragment() {

    private lateinit var binding: ProfessionFragmentBinding
    private lateinit var professionAdapter: ProfessionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfessionFragmentBinding.inflate(inflater)
        binding.nextButton.setOnClickListener { onClickNext() }
        return binding.root
    }

    private fun onClickNext() {
        val selectedProfessions = professionAdapter.getSelectedProfessions()
        if (selectedProfessions.isEmpty()) {
            return
        }
        val tagFragment = TagFragment()
        val bundle = Bundle().apply {
            putParcelableArrayList(
                LIST_SELECTED_PROFESSIONS_KEY,
                selectedProfessions
            )
            putParcelableArrayList(
                LIST_SELECTED_TAGS_KEY,
                arrayListOf()
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
            findNavController().navigate(R.id.action_professionFragment_to_tagsFragment)
        }

        buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_professionFragment_to_signUpFragment)
        }
        with(binding) {
            val professions = getProfessions()
            val selectedProfessions =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelableArrayList(
                        LIST_SELECTED_PROFESSIONS_KEY,
                        Profession::class.java
                    )!!
                } else {
                    arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
                }
            professionAdapter = ProfessionAdapter(professions, selectedProfessions)
            professionList.adapter = professionAdapter
            professionList.layoutManager = LinearLayoutManager(professionList.context)
        }
    }
}
