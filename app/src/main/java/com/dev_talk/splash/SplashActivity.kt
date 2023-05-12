package com.dev_talk.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev_talk.R
import com.dev_talk.auth.AuthActivity
import com.dev_talk.main.MainActivity
import com.dev_talk.utils.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.decorView.postDelayed({
            val user = FirebaseAuth.getInstance().currentUser
            val uid = user?.uid
            val db = FirebaseDatabase.getInstance(DATABASE_URL).reference
            if (uid != null) {
                db.child("users").child(uid).addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            startMain()
                        } else {
                            user.delete()
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

    private fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun startAuth() {
        startActivity(Intent(this, AuthActivity::class.java))
    }

    companion object {
        private const val SPLASH_DELAY_TIME = 3000L
    }
}
