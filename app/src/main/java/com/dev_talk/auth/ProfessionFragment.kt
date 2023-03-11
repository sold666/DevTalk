package com.dev_talk.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.LIST_PROFESSIONS_KEY
import com.dev_talk.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.LIST_SELECTED_TAGS_KEY
import com.dev_talk.main.R
import com.dev_talk.main.databinding.ProfessionFragmentBinding
import com.dev_talk.structures.Profession

class ProfessionFragment : Fragment() {

    private var binding: ProfessionFragmentBinding? = null
    private val _binding get() = binding!!
    private lateinit var professionAdapter: ProfessionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfessionFragmentBinding.inflate(inflater)

        binding!!.nextButton.setOnClickListener { onClickNext() }
        binding!!.backButton.setOnClickListener { onClickBack() }

        return _binding.root
    }

    private fun onClickNext() {
        val selectedProfessions = professionAdapter.getSelectedProfessions()
        if (selectedProfessions.isEmpty()) {
            return
        }
        val tagFragment = TagFragment()
        val bundle = Bundle().apply {
            putParcelableArrayList(
                LIST_SELECTED_PROFESSIONS_KEY,
                selectedProfessions
            )
        }
        bundle.apply {
            putParcelableArrayList(
                LIST_SELECTED_TAGS_KEY,
                arrayListOf()
            )
        }
        tagFragment.arguments = bundle

        requireFragmentManager()
            .beginTransaction()
            .replace(R.id.container, tagFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun onClickBack() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(_binding) {
            val professions = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(LIST_PROFESSIONS_KEY, Profession::class.java)!!
            } else {
                arguments?.getParcelableArrayList(LIST_PROFESSIONS_KEY)!!
            }
            val selectedProfessions = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY, Profession::class.java)!!
            } else {
                arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
            }
            professionAdapter = ProfessionAdapter(professions, selectedProfessions)
            professionList.adapter = professionAdapter
            professionList.layoutManager = LinearLayoutManager(professionList.context)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
