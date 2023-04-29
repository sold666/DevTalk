package com.dev_talk.main.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }
}
