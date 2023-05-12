package com.dev_talk.main.profile.edit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.databinding.FragmentProfileEditBinding
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.Link
import com.dev_talk.main.structures.ProfileData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ProfileEditFragment : Fragment() {
    private lateinit var binding: FragmentProfileEditBinding
    private lateinit var data: MutableList<ProfileData>
    private lateinit var userAvatar: CircleImageView
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileEditBinding.inflate(inflater)
        auth = Firebase.auth
        storage = FirebaseStorage.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args: ProfileEditFragmentArgs by navArgs()
            data = args.listProfileData.toMutableList()
            userAvatar = avatar
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
                findNavController().navigate(R.id.action_profileEditFragment_to_profileInformationFragment)
            }
        }
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri) {
        val uploadTask =
            storage.reference.child("users/" + auth.currentUser?.uid.toString() + "/profile_avatar.jpg")
        uploadTask.putFile(imageUri).addOnSuccessListener {
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
            adapter = EditProfileProfessionsAndTagsAdapter(data)
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
