package com.dev_talk.main.profile.information

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.databinding.FragmentProfileInformationBinding
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.Link
import com.dev_talk.main.structures.ProfileData
import com.dev_talk.main.structures.UserTags
import com.dev_talk.utils.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ProfileInformationFragment : Fragment() {
    private lateinit var binding: FragmentProfileInformationBinding
    private var isNightModeOn: Boolean = false
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var listProfileData: ArrayList<ProfileData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileInformationBinding.inflate(inflater)
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenMenuButtons()
        with(binding) {
            setDataFromDatabase()
            setUpLinks(socialNetwork)
            isNightModeOn =
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            switchThemeButton.setImageResource(if (isNightModeOn) R.drawable.moon else R.drawable.sun)

            switchThemeButton.setOnClickListener {
                val newMode = if (isNightModeOn) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                AppCompatDelegate.setDefaultNightMode(newMode)
                switchThemeButton.setImageResource(if (isNightModeOn) R.drawable.sun else R.drawable.moon)
                isNightModeOn = !isNightModeOn
                val toastText = if (isNightModeOn) context?.getString(R.string.night_mode_on) else context?.getString(R.string.night_mode_off)
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun listenMenuButtons() {
        val toolbar = binding.profileAppBar
        if (toolbar.menu.size() == 0) {
            toolbar.inflateMenu(R.menu.profile_app_bar)
        }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit -> {
                    val action =
                        ProfileInformationFragmentDirections.actionProfileInformationFragmentToProfileEditFragment(
                            listProfileData.toTypedArray()
                        )
                    findNavController().navigate(action)
                    true
                }

                R.id.log_out -> {
                    auth.signOut()
                    findNavController().navigate(R.id.action_profileInformationFragment_to_authActivity)
                    true
                }

                R.id.delete_profile -> {
                    val user = Firebase.auth.currentUser!!
                    val alertDialog = context?.let { AlertDialog.Builder(it) }
                    alertDialog!!.setTitle(context?.getString(R.string.alert_title_for_delete_profile))
                    alertDialog.setMessage(context?.getString(R.string.alert_message_for_delete_profile))
                    alertDialog.setPositiveButton(
                        context?.getString(R.string.alert_positive_button_for_delete_profile),
                        DialogInterface.OnClickListener { dialog, _ ->
                            db.child("users").child(user.uid).removeValue().addOnCompleteListener {
                                user.delete()
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                context?.getString(R.string.account_delete_message),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            findNavController().navigate(R.id.action_profileInformationFragment_to_authActivity)
                                        } else {
                                            Toast.makeText(
                                                context,
                                                context?.getString(R.string.something_wrong),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            }
                        })
                    alertDialog.setNegativeButton(
                        (R.string.alert_negative_button_for_delete_profile),
                        DialogInterface.OnClickListener { dialog, _ ->
                            dialog.dismiss()
                        })
                    alertDialog.create()
                    alertDialog.show()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun setUpLinks(recyclerView: RecyclerView) {
        val manager = LinearLayoutManager(context)
        manager.orientation = RecyclerView.HORIZONTAL
        recyclerView.apply {
            layoutManager = manager
            adapter = ProfileLinkAdapter(getLinks())
            setHasFixedSize(true)
        }
    }

    private fun getLinks(): ArrayList<Link> {
        return arrayListOf(
            Link(R.drawable.ic_person)
        )
    }

    private fun setUpRecyclerView(
        recyclerView: RecyclerView,
        data: ArrayList<ProfileData>
    ) {
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
            adapter = ProfileInformationProfessionsAndTagsAdapter(data)
        }
    }

    private fun getTagsIcon(tag: String): Int {
        return when (tag) {
            "Spring Boot" -> R.drawable.spring_boot
            "Django" -> R.drawable.django
            "Flask" -> R.drawable.flask
            "Angular" -> R.drawable.angular
            "React" -> R.drawable.react
            "Vue.js" -> R.drawable.vuejs
            "Unity" -> R.drawable.unity
            "Unreal Engine" -> R.drawable.unreal_engine
            "Godot" -> R.drawable.godot
            "Machine Learning" -> R.drawable.machine_learning
            "Data Mining" -> R.drawable.data_mining
            "Data Analysis" -> R.drawable.data_analysis
            "Android SDK" -> R.drawable.android_sdk
            "Flutter" -> R.drawable.flutter
            "React Native" -> R.drawable.react
            "Adobe XD" -> R.drawable.adobe_xd
            "Figma" -> R.drawable.figma
            "Sketch" -> R.drawable.sketch
            "InVision" -> R.drawable.invision
            "Penetration testing" -> R.drawable.penetration_testing
            "Network Security" -> R.drawable.network_security
            "Cryptography" -> R.drawable.cryptography
            "Vulnerability Assessment" -> R.drawable.vulnerability_assessment
            "Docker" -> R.drawable.docker
            "Kubernetes" -> R.drawable.kubernetes
            "AWS" -> R.drawable.aws
            "Jenkins" -> R.drawable.jenkins
            "CI CD" -> R.drawable.ci_cd
            "Solidity" -> R.drawable.solidity
            "Networks" -> R.drawable.networks
            "Hyperledger Fabric" -> R.drawable.hyperledger_fabric
            "Smart Contracts" -> R.drawable.smart_contracts
            "Decentralized Applications (dApps)" -> R.drawable.dapps
            else -> R.drawable.default_avatar_tag
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
                with(binding) {
                    name.text = username
                }
                setUpRecyclerView(recyclerView = binding.userInfoList, listProfileData)
            } else {
                Log.d("userData", "Error!")
            }
        }.addOnFailureListener { e ->
            Log.d("userData", e.message.toString())
        }
    }
}
