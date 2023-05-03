package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.databinding.FragmentSignUpBinding
import com.dev_talk.dto.User
import com.dev_talk.utils.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            val name = binding.name
            val surname = binding.surname
            val email = binding.email
            val password = binding.password
            register(
                name.text.toString().trim(),
                surname.text.toString().trim(),
                email.text.toString().trim(),
                password.text.toString().trim()
            )
        }

        binding.signInButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun register(name: String, surname: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    addNewUser(
                        name,
                        surname,
                        email,
                        password
                    )
                    findNavController().navigate(R.id.action_signUpFragment_to_professionFragment)
                } else {
                    Toast.makeText(
                        context,
                        "Some error occurred.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun addNewUser(
        name: String,
        surname: String,
        email: String,
        password: String,
    ) {
        db.child("users").child(auth.currentUser?.uid!!)
            .setValue(User(name, surname, email, password, emptyList(), emptyList()))
    }
}
