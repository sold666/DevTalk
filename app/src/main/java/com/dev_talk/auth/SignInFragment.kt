package com.dev_talk.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dev_talk.AUTH
import com.dev_talk.R
import com.dev_talk.databinding.SignInFragmentBinding

class SignInFragment : Fragment() {

    private var binding: SignInFragmentBinding? = null
    private val _binding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignInFragmentBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = binding?.username
        val password = binding?.password
        val buttonLogin = binding?.loginButton
        val buttonRegister = binding?.registerButton

        buttonRegister?.setOnClickListener {
            AUTH.navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
