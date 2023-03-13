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

class ProfessionFragment : Fragment() {

    private lateinit var binding: ProfessionFragmentBinding
    private lateinit var professionAdapter: ProfessionAdapter

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
            val bundle = Bundle().apply {
                putParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    professionAdapter.getSelectedProfessions()
                )
            }
            findNavController().navigate(R.id.action_professionFragment_to_tagsFragment, bundle)
        }

        buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_professionFragment_to_signUpFragment)
        }

        with(binding) {
            val professions = getProfessions()
            professionAdapter = ProfessionAdapter(professions, arrayListOf()) //todo
            professionList.adapter = professionAdapter
            professionList.layoutManager = LinearLayoutManager(professionList.context)
        }
    }
}
