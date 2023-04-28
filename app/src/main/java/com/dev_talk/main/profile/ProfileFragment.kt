package com.dev_talk.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.dev_talk.R
import com.dev_talk.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var isNightModeOn: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

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
                val toastText = if (isNightModeOn) "Night mode ON" else "Night mode OFF"
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
