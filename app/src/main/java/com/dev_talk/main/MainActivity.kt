package com.dev_talk.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dev_talk.R
import com.dev_talk.databinding.ActivityMainBinding
import com.dev_talk.main.profile.ProfileCache
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.ProfileData
import com.dev_talk.main.structures.UserTags
import com.dev_talk.utils.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var storage: FirebaseStorage

    private lateinit var listProfileData: ArrayList<ProfileData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        storage = FirebaseStorage.getInstance()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setUpNavigationView()
        setDataFromDatabase()
    }

    private fun setUpNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        mainBinding.mainBottomNavView.setupWithNavController(navController)
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
            Log.d("userData", e.message.toString())
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
}
