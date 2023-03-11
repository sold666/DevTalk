package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dev_talk.AUTH
import com.dev_talk.R
import com.dev_talk.databinding.SignUpFragmentBinding

class SignUpFragment : Fragment() {

    private var binding: SignUpFragmentBinding? = null
    private val _binding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignUpFragmentBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = binding?.name
        val surname = binding?.surname
        val username = binding?.username
        val password = binding?.password
        val buttonNext =  binding?.nextButton
        val buttonLogin = binding?.signInButton

        buttonNext?.setOnClickListener {
            AUTH.navController.navigate(R.id.action_signUpFragment_to_professionFragment)
        }

        buttonLogin?.setOnClickListener {
            AUTH.navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
