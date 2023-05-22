package com.dev_talk.auth.result

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.common.structures.ProfessionDto
import com.dev_talk.common.structures.TagDto
import com.dev_talk.databinding.FragmentResultBinding
import com.dev_talk.dto.User
import com.dev_talk.main.profile.ProfileCache
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.ProfileData
import com.dev_talk.main.structures.UserTags
import com.dev_talk.utils.DATABASE_URL
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.LIST_SELECTED_TAGS_KEY
import com.dev_talk.utils.USER_KEY
import com.dev_talk.utils.getTagsIcon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.stream.Collectors

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private lateinit var selectedTags: List<TagDto>
    private lateinit var selectedProfessions: List<ProfessionDto>
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var user: User
    private lateinit var storage: FirebaseStorage
    private lateinit var listProfileData: ArrayList<ProfileData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        binding = FragmentResultBinding.inflate(inflater)
        storage = FirebaseStorage.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.progressBar
        progressText = binding.progressText
        initListeners()
        with(binding) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                selectedTags = arguments?.getParcelableArrayList(
                    LIST_SELECTED_TAGS_KEY,
                    TagDto::class.java
                )!!
                selectedProfessions = arguments?.getParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    ProfessionDto::class.java
                )!!
                user = arguments?.getParcelable(
                    USER_KEY,
                    User::class.java
                )!!
            } else {
                selectedTags = arguments?.getParcelableArrayList(LIST_SELECTED_TAGS_KEY)!!
                selectedProfessions =
                    arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
                user = arguments?.getParcelable(USER_KEY)!!
            }
            val listData = generateMapFromArrays()
            val titleList = selectedProfessions.map { it.name }
            val resultAdapter = ResultAdapter(resultList.context, listData, titleList)
            resultList.setAdapter(resultAdapter)
            for (i in listData.keys.indices) {
                resultList.expandGroup(i)
            }
        }
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener { findNavController().popBackStack() }

        binding.nextButton.setOnClickListener {
            addDataForUser(
                selectedProfessions
            )

            binding.resultList.visibility = View.GONE
            binding.backButton.visibility = View.GONE
            binding.nextButton.visibility = View.GONE
            binding.resultText.visibility = View.GONE
            binding.text.visibility = View.GONE
            progressBar.isVisible = true
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val progress = ((5000 - millisUntilFinished) / 50).toInt()
                    progressBar.progress = progress
                    progressText.text = context?.getString(R.string.processing_data)
                }

                override fun onFinish() {
                    progressText.text = context?.getString(R.string.is_ready_text)
                    progressBar.isVisible = false
                    activity?.finish()
                    findNavController().navigate(R.id.action_resultFragment_to_mainActivity)
                }
            }.start()
        }

    }

    private fun generateMapFromArrays(): Map<String, List<String>> {
        val expandableListMap = HashMap<String, List<String>>()
        for (profession in selectedProfessions) {
            val tagsNamesList = profession.tags.filter { selectedTags.contains(it) }.map { it.name }
            expandableListMap[profession.name] = tagsNamesList
        }
        return expandableListMap
    }

    private fun addDataForUser(
        professions: List<ProfessionDto>,
    ) {
        val map: MutableMap<String, List<String>> = mutableMapOf()

        listProfileData = arrayListOf()
        professions.forEach {
            listProfileData.add(Header(it.name))
            val selectedTags: List<String> =
                if (it.tags.any { t -> t.isSelected }) {
                    it.tags.stream().filter { t -> t.isSelected }.map { it.name }.collect(
                        Collectors.toList()
                    )
                } else {
                    listOf("")
                }
            val items = arrayListOf<Item>()
            selectedTags.forEach { tag ->
                if (tag != "") {
                    items.add(Item(UserTags(getTagsIcon(tag), tag)))
                }
            }
            listProfileData.addAll(items)
            map[it.name] = selectedTags
        }
        db.child("users").child(auth.currentUser?.uid!!).setValue(user)
        db.child("users")
            .child(auth.currentUser?.uid!!)
            .child("user_info")
            .setValue(map)
        updateUserStatus("online")
    }

    private fun updateUserStatus(state : String) {
        val saveCurrentDate: String
        val saveCurrentTime: String

        val calendarForDate = Calendar.getInstance()
        val currentDate = SimpleDateFormat("MMM dd, yyyy")
        saveCurrentDate = currentDate.format(calendarForDate.time)

        val calendarForTime = Calendar.getInstance()
        val currentTime = SimpleDateFormat("hh:mm a")
        saveCurrentTime = currentTime.format(calendarForTime.time)

        val currentStateMap : MutableMap<String, Any> = mutableMapOf()

        currentStateMap["time"] = saveCurrentTime
        currentStateMap["date"] = saveCurrentDate
        currentStateMap["state"] = state

        db.child("users").child(auth.currentUser?.uid!!).child("user_state").updateChildren(currentStateMap)

        ProfileCache.name = user.name + " " + user.surname
        ProfileCache.profileData = listProfileData
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
                ProfileCache.name = username
                ProfileCache.profileData = listProfileData
                storage.reference
                    .child("users/" + auth.currentUser?.uid.toString() + "/profile_avatar.jpg")
                    .downloadUrl
                    .addOnSuccessListener {
                        ProfileCache.avatar = it
                    }
            } else {
                Log.d("userData", "Error!")
            }
        }.addOnFailureListener { e ->
            println(e.message.toString())
        }
    }
}
