package com.dev_talk.main.profile.edit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.common.structures.ProfessionDto
import com.dev_talk.common.structures.TagDto
import com.dev_talk.databinding.FragmentProfileEditBinding
import com.dev_talk.main.profile.ProfileCache
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.Link
import com.dev_talk.main.structures.ProfileData
import com.dev_talk.utils.DATABASE_URL
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.LIST_SELECTED_TAGS_KEY
import com.dev_talk.utils.USER_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.stream.Collectors

class ProfileEditFragment : Fragment() {
    private lateinit var binding: FragmentProfileEditBinding
    private lateinit var data: MutableList<ProfileData>
    private lateinit var userAvatar: CircleImageView
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var db: DatabaseReference
    private lateinit var onDeleteBtnClickListener: (data: MutableList<ProfileData>, position: Int) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileEditBinding.inflate(inflater)
        auth = Firebase.auth
        storage = FirebaseStorage.getInstance()
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        with(binding) {
            data = ProfileCache.profileData.toMutableList()

            nameInput.setText(ProfileCache.name)
            userAvatar = avatar

            if (ProfileCache.avatar != null) {
                Picasso.get().load(ProfileCache.avatar).into(avatar)
            }

            setUpUserProfessionsAndTags(recyclerView = userInfoList)
            setUpLinks(socialNetwork)

            launcher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val resultData: Intent? = result.data
                        val imageURI = resultData?.data
                        if (imageURI != null) {
                            uploadImageToFirebaseStorage(imageURI)
                        }
                    }
                }

            userAvatar.setOnClickListener {
                val openGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                launcher.launch(openGallery)
            }

            saveAllBtn.setOnClickListener {
                val username = nameInput.text.toString()

                val splittedUsername = username.split(" ")
                if (splittedUsername.size != 2) {
                    Toast.makeText(
                        context,
                        context?.getString(R.string.invalid_username),
                        Toast.LENGTH_LONG,
                    ).show()
                    return@setOnClickListener
                }

                updateName(username)
                updateTags(data)
                findNavController().navigate(R.id.action_profileEditFragment_to_profileInformationFragment)
            }
        }
    }

    private fun updateName(username: String) {
        val splittedUsername = username.split(" ")
        ProfileCache.name = username

        val name = splittedUsername[0]
        val surname = splittedUsername[1]

        val user = db.child("users").child(auth.currentUser?.uid!!)
        user.child("name").setValue(name)
        user.child("surname").setValue(surname)
    }

    private fun updateTags(data: MutableList<ProfileData>) {
        ProfileCache.profileData = data as ArrayList<ProfileData>

        val map: MutableMap<String, List<String>> = mutableMapOf()

        var currentProfession = ""
        var currentTags = mutableListOf<String>()
        data.forEach {
            if (it is Header) {
                if (currentProfession != "") {
                    if (currentTags.isEmpty()) {
                        currentTags = mutableListOf("")
                    }
                    map[currentProfession] = currentTags
                }
                currentTags = mutableListOf()
                currentProfession = it.title
            }
            if (it is Item) {
                currentTags.add(it.userTags.tag)
            }
        }
        if (currentTags.isEmpty()) {
            currentTags = mutableListOf("")
        }
        map[currentProfession] = currentTags

        db.child("users")
            .child(auth.currentUser?.uid!!)
            .child("user_info")
            .setValue(map)
    }

    private fun initListeners() {
        onDeleteBtnClickListener = { data, position ->
            data.removeAt(position)
            this.data = data
        }
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri) {
        val uploadTask =
            storage.reference.child("users/" + auth.currentUser?.uid.toString() + "/profile_avatar.jpg")
        uploadTask.putFile(imageUri).addOnSuccessListener {
            ProfileCache.avatar = imageUri
            Picasso.get().load(imageUri).into(userAvatar)
            Toast.makeText(
                context,
                context?.getString(R.string.avatar_message_true),
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener {
            Toast.makeText(
                context,
                context?.getString(R.string.avatar_message_false),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setUpUserProfessionsAndTags(recyclerView: RecyclerView) {
        val manager = object : GridLayoutManager(context, 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        manager.apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (data[position]) {
                        is Header -> 2
                        is Item -> 1
                    }
                }
            }
            orientation = RecyclerView.VERTICAL
        }
        recyclerView.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = manager
            adapter = EditProfileProfessionsAndTagsAdapter(data, onDeleteBtnClickListener)
        }
    }

    private fun setUpLinks(recyclerView: RecyclerView) {
        val manager = object : LinearLayoutManager(context) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        manager.orientation = RecyclerView.HORIZONTAL
        recyclerView.apply {
            layoutManager = manager
            adapter = EditProfileLinkAdapter(getLinks())
            setHasFixedSize(true)
        }
    }

    private fun getLinks(): ArrayList<Link> {
        return arrayListOf(
            Link(R.drawable.ic_add_new_chat_btn)
        )
    }
}
