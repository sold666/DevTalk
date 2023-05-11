package com.dev_talk.main.recommended_chats

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.databinding.FragmentNewChatBinding
import com.dev_talk.main.structures.Chat
import com.dev_talk.utils.DATABASE_URL
import com.dev_talk.utils.getProfessions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID
import kotlin.math.log

class NewChatFragment : Fragment() {
    private lateinit var binding: FragmentNewChatBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var chatAvatar: CircleImageView
    private lateinit var launcher: ActivityResultLauncher<Intent>

    private lateinit var chosenProfessionName: String
    private lateinit var chosenTagName: String
    private lateinit var chatName: String
    private var imageURI: Uri? = null

    private var isNameValid = false
    private var isTagValid = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("on create", "asdsds")
        auth = Firebase.auth
        storage = FirebaseStorage.getInstance()
        binding = FragmentNewChatBinding.inflate(inflater)
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        chatAvatar = binding.chatImage
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("on created", "asdsds")
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeNavigationCallBack()
        updateDoneButtonStatus()

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val resultData: Intent? = result.data
                    imageURI = resultData?.data
                    Log.d("image", imageURI.toString())
                    if (imageURI != null) {
                        Picasso.get().load(imageURI).into(chatAvatar)
                    }
                }
            }

    }



    private fun initListeners() {
        binding.chatName.doOnTextChanged { text, start, before, count ->
            chatName = text.toString()
            isNameValid = chatName.isNotEmpty()
            updateDoneButtonStatus()
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.chooseTagButton.setOnClickListener {
            findNavController().navigate(R.id.action_newChatFragment_to_tagChoiceFragment)
        }

        binding.doneButton.setOnClickListener {
            val chosenTag = getProfessions()
                .first { it.name == chosenProfessionName }.tags
                .first { it.name == chosenTagName }

            println("CHAT NAME: " + chatName)
            println("TAG: " + chosenTag)
            createChat(chatName, chosenTagName)
            findNavController().popBackStack()
        }



        chatAvatar.setOnClickListener {
            val openGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            launcher.launch(openGallery)
        }
    }


    private fun createChat(chatName: String, chatTag: String) {
        val map: MutableMap<String, String> = mutableMapOf()
        val id: String = UUID.randomUUID().toString()
        map.put("name", chatName)
        map.put("tag", chatTag)

        val ref: DatabaseReference = db.child("chats")
        ref.child(id).setValue(map)
            .addOnSuccessListener {
                val userData: MutableMap<String, String> = mutableMapOf()
                userData.put("id", auth.currentUser?.uid!!)
                userData.put("role", "admin")
                ref.child(id)
                    .child("participants")
                    .child(auth.currentUser?.uid!!).setValue(userData)
//                if (imageURI == null) {
//                    uploadImageToFirebaseStorage(id, )
//                } else {
                    uploadImageToFirebaseStorage(id, imageURI!!)
                //}

            }
            .addOnFailureListener { e ->
                Log.d("chat creation" , e.message.toString())
                Toast.makeText(
                    context,
                    "Failed to create chat.Try later.",
                    Toast.LENGTH_LONG,
                ).show()
            }
    }

    private fun uploadImageToFirebaseStorage(chatId: String, imageUri: Uri) {
        val uploadTask =
            storage.reference.child("chats/" + chatId + "/chat_avatar.jpg")
        uploadTask.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(context, context?.getString(R.string.avatar_message_true), Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, context?.getString(R.string.avatar_message_false), Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateDoneButtonStatus() {
        binding.doneButton.isEnabled = isNameValid && isTagValid
    }

    private fun handleTagChoice(newTest: String) {
        val buttonText = (context?.getString(R.string.chosen_chat_tag) ?: "") + ": " + newTest
        binding.chooseTagButton.text = buttonText
        isTagValid = true
        updateDoneButtonStatus()
    }

    private fun observeNavigationCallBack() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("profession")
            ?.observe(viewLifecycleOwner) {
                chosenProfessionName = it
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("tag")
            ?.observe(viewLifecycleOwner) {
                chosenTagName = it
                handleTagChoice(chosenTagName)
            }
    }
}
