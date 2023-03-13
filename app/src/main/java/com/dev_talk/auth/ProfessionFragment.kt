package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.*
import com.dev_talk.databinding.ProfessionFragmentBinding
import com.dev_talk.structures.Profession

class ProfessionFragment : Fragment() {

    private lateinit var binding: ProfessionFragmentBinding
    private lateinit var professionAdapter: ProfessionAdapter
    private var selectedProfessions: ArrayList<Profession> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfessionFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonNext = binding.nextButton
        val buttonBack = binding.backButton

        buttonNext.setOnClickListener {
            selectedProfessions = professionAdapter.getSelectedProfessions()
            val bundle = Bundle().apply {
                putParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    selectedProfessions
                )
            }
            if (selectedProfessions.isNotEmpty()) {
                findNavController().navigate(R.id.action_professionFragment_to_tagsFragment, bundle)
            }
        }

        buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        with(binding) {
            val professions = getProfessions()
            professionAdapter = ProfessionAdapter(professions, selectedProfessions)
            professionList.adapter = professionAdapter
            professionList.layoutManager = LinearLayoutManager(professionList.context)
        }
    }
}
