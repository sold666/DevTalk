package com.dev_talk.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonLogin = binding.loginButton
        val buttonRegister = binding.registerButton

        buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        buttonLogin.setOnClickListener {
            val email = binding.email
            val password = binding.password

            login(email.text.toString().trim(), password.text.toString().trim())
        }
    }

    private fun login(email: String, password: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = context?.getString(R.string.validation_email_message)
        } else if (TextUtils.isEmpty(password)) {
            binding.password.error = context?.getString(R.string.validation_password_message)
        } else if (password.length < 6) {
            binding.password.error = context?.getString(R.string.validation_password_message_two)
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
                    } else {
                        Toast.makeText(
                            context,
                            context?.getString(R.string.auth_failed_message),
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
        }

    }
}
