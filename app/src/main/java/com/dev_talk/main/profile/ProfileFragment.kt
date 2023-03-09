package com.dev_talk.main.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.main.databinding.FragmentProfileBinding
import com.dev_talk.main.structures.Profession

private const val DEFAULT_LIST_PROFESSIONS_KEY = "professions"

public class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private val _binding: FragmentProfileBinding
        get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(_binding) {
            val professions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY, Profession::class.java)!!
            } else {
                arguments?.getParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY)!!
            }
            with (myChats) {
                adapter = ProfileChatsAdapter(professions)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return _binding.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(professions: ArrayList<Profession>) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY, professions)
                }
            }
    }
}