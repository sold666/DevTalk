package com.dev_talk.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dev_talk.R
import com.dev_talk.databinding.ActivityMainBinding
import com.dev_talk.utils.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var onlineUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        storage = FirebaseStorage.getInstance()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        onlineUserId = auth.currentUser!!.uid
        setContentView(mainBinding.root)
        setUpNavigationView()
    }

    override fun onStart() {
        super.onStart()
        updateUserStatus("online")
    }

    override fun onStop() {
        super.onStop()
        updateUserStatus("offline")
    }

    override fun onDestroy() {
        super.onDestroy()
        updateUserStatus("offline")
    }

    private fun setUpNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        mainBinding.mainBottomNavView.setupWithNavController(navController)
    }

    private fun updateUserStatus(state: String) {
        val saveCurrentDate: String
        val saveCurrentTime: String

        val calendarForDate = Calendar.getInstance()
        val currentDate = SimpleDateFormat("MMM dd, yyyy")
        saveCurrentDate = currentDate.format(calendarForDate.time)

        val calendarForTime = Calendar.getInstance()
        val currentTime = SimpleDateFormat("hh:mm a")
        saveCurrentTime = currentTime.format(calendarForTime.time)

        val currentStateMap: MutableMap<String, Any> = mutableMapOf()

        currentStateMap["time"] = saveCurrentTime
        currentStateMap["date"] = saveCurrentDate
        currentStateMap["state"] = state

        Log.d("db", db.toString())
        db.child("users").child(onlineUserId).child("user_state").updateChildren(currentStateMap)
    }
}
