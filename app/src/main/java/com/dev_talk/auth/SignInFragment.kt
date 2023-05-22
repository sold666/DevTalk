package com.dev_talk.auth

import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.common.structures.LinkTypeConverter
import com.dev_talk.databinding.FragmentSignInBinding
import com.dev_talk.main.profile.ProfileCache
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.Link
import com.dev_talk.main.structures.LinkType
import com.dev_talk.main.structures.ProfileData
import com.dev_talk.main.structures.UserTags
import com.dev_talk.utils.DATABASE_URL
import com.dev_talk.utils.getTagsIcon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var listProfileData: ArrayList<ProfileData>
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        binding = FragmentSignInBinding.inflate(inflater)
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        storage = FirebaseStorage.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.progressBar
        progressText = binding.progressText

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
                        setDataFromDatabase()
                        binding.email.visibility = View.GONE
                        binding.text.visibility = View.GONE
                        binding.loginButton.visibility = View.GONE
                        binding.messageIcon.visibility = View.GONE
                        binding.password.visibility = View.GONE
                        binding.questionText.visibility = View.GONE
                        binding.welcomeText.visibility = View.GONE
                        binding.registerButton.visibility = View.GONE

                        progressText.isVisible = true
                        progressBar.isVisible = true
                        object : CountDownTimer(3000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                val progress = ((5000 - millisUntilFinished) / 50).toInt()
                                progressBar.progress = progress
                                progressText.text = context?.getString(R.string.wait)
                            }

                            override fun onFinish() {
                                progressBar.isVisible = false
                                progressText.isVisible = false
                                //activity?.finish()
                                findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
                            }
                        }.start()
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

    private fun setDataFromDatabase() {
        db.child("users").child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                listProfileData = arrayListOf()
                val username = it.child("name").value.toString() + " " + it.child("surname").value
                val professions = it.child("user_info")
                professions.children.forEach { profession ->
                    val header: String = profession.key!!
                    val tags: List<String> = profession.getValue<List<String>>()!!
                    listProfileData.add(Header(header))
                    val items = arrayListOf<Item>()
                    tags.forEach { tag ->
                        if (tag != "") {
                            items.add(Item(UserTags(getTagsIcon(tag), tag)))
                        }
                    }
                    listProfileData.addAll(items)
                }
                val linksData = arrayListOf<Link>()
                it.child("user_links")
                    .children.forEach { link ->
                        val type = LinkTypeConverter.types.getOrDefault(link.key, LinkType.GITHUB)
                        val string = LinkTypeConverter.strings.getOrDefault(type, "Github")
                        val current = Link(
                            LinkTypeConverter.links.getOrDefault(string, R.drawable.ic_leave),
                            link.value as String,
                            type
                        )
                        linksData.add(current)
                    }
                ProfileCache.name = username
                ProfileCache.profileData = listProfileData
                ProfileCache.links = linksData
//                storage.reference
//                    .child("users/" + auth.currentUser?.uid.toString() + "/profile_avatar.jpg")
//                    .downloadUrl
//                    .addOnSuccessListener {
//                        ProfileCache.avatar = it
//                    }
            } else {
                Log.d("userData", "Error!")
            }
        }.addOnFailureListener { e ->
            println(e.message.toString())
        }
    }
}
