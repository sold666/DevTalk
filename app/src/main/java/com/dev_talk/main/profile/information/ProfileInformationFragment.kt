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
import com.dev_talk.main.profile.ProfileCache
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.Link
import com.dev_talk.main.structures.ProfileData
import com.dev_talk.utils.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar

class ProfileInformationFragment : Fragment() {
    private lateinit var binding: FragmentProfileInformationBinding
    private var isNightModeOn: Boolean = false
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileInformationBinding.inflate(inflater)
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance(DATABASE_URL).reference
        storage = FirebaseStorage.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenMenuButtons()
        with(binding) {
            name.text = ProfileCache.name
            status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.status_online, 0, 0, 0)
            status.text = context?.getString(R.string.status_online)
            setUpRecyclerView(recyclerView = binding.userInfoList, ProfileCache.profileData)

            if (ProfileCache.avatar != null) {
                Picasso.get().load(ProfileCache.avatar).into(avatar)
            }

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
                val toastText =
                    if (isNightModeOn) context?.getString(R.string.night_mode_on) else context?.getString(
                        R.string.night_mode_off
                    )
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
                    findNavController().navigate(R.id.action_profileInformationFragment_to_profileEditFragment)
                    true
                }

                R.id.log_out -> {
                    updateUserStatus("offline")
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
                                            Log.d("error", task.exception?.message.toString())
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
            Link(R.drawable.ic_links)
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

        db.child("users").child(auth.currentUser?.uid!!).child("user_state")
            .updateChildren(currentStateMap)
    }
}