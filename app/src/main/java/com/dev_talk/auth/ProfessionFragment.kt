package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.databinding.ProffesionFragmentBinding

class ProfessionFragment : Fragment() {

    private lateinit var binding: ProffesionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProffesionFragmentBinding.inflate(inflater)
        return binding.root
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
    }
}
