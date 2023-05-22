package com.dev_talk.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dev_talk.R
import com.dev_talk.auth.AuthActivity
import com.dev_talk.main.MainActivity
import com.dev_talk.main.profile.ProfileCache
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.ProfileData
import com.dev_talk.main.structures.UserTags
import com.dev_talk.utils.DATABASE_URL
import com.dev_talk.utils.getTagsIcon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var listProfileData: ArrayList<ProfileData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        storage = FirebaseStorage.getInstance()
        setContentView(R.layout.activity_splash_screen)
        window.decorView.postDelayed({
            var user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            db = FirebaseDatabase.getInstance(DATABASE_URL).reference
            if (uid != null) {
                setDataFromDatabase()
                db.child("users").child(uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                startMain()
                            } else {
                                user?.delete()
                                startAuth()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
            } else {
                startAuth()
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, SPLASH_DELAY_TIME)
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

    fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun startAuth() {
        startActivity(Intent(this, AuthActivity::class.java))
    }

    companion object {
        private const val SPLASH_DELAY_TIME = 3000L
    }
}
