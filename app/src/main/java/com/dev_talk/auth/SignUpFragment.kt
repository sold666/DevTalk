package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = binding.name
        val surname = binding.surname
        val username = binding.username
        val password = binding.password
        val buttonNext = binding.nextButton
        val buttonLogin = binding.signInButton

        buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_professionFragment)
        }

        buttonLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
