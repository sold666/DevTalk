package com.dev_talk.auth.profession

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.R
import com.dev_talk.auth.AuthActivity
import com.dev_talk.auth.structures.Profession
import com.dev_talk.databinding.FragmentProfessionBinding
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.getProfessions

class ProfessionFragment : Fragment() {

    private lateinit var binding: FragmentProfessionBinding
    private lateinit var professionAdapter: ProfessionAdapter
    private lateinit var onProfessionsClickListener: (profession: Profession, adapterPosition: Int) -> Unit
    private var professions: List<Profession> = getProfessions()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfessionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if ((requireActivity() as? AuthActivity)?.isAppRestarted == true) {
            clearSavedValues(
                requireContext().getSharedPreferences(
                    "buttons_prefs",
                    Context.MODE_PRIVATE
                )
            )
            (requireActivity() as? AuthActivity)?.isAppRestarted = false
        }
        initListeners()
        with(binding) {
            nextButton.isEnabled = false
            professionAdapter = ProfessionAdapter(onProfessionsClickListener)
            professionList.adapter = professionAdapter
            professionList.itemAnimator = null
            professionList.layoutManager = LinearLayoutManager(professionList.context)
        }
        professionAdapter.setData(professions)
        updateNextButtonStatus()
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
            clearSavedValues(
                requireContext().getSharedPreferences(
                    "buttons_prefs",
                    Context.MODE_PRIVATE
                )
            )
        }

        binding.nextButton.setOnClickListener {
            val selectedProfessions = professions.filter { it.isSelected }.sortedBy { it.id }
            val bundle = Bundle().apply {
                putParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    ArrayList(selectedProfessions)
                )
            }
            findNavController().navigate(R.id.action_professionFragment_to_tagsFragment, bundle)
        }

        onProfessionsClickListener = { profession, position ->
            professions.forEach {
                if (it.id == profession.id) {
                    profession.isSelected = !profession.isSelected
                }
            }
            professionAdapter.setData(professions, position)
            updateNextButtonStatus()
            saveProfessionsState()
        }
        loadProfessionsState()
    }

    private fun updateNextButtonStatus() {
        binding.nextButton.isEnabled = professions.any { it.isSelected }
    }

    private fun saveProfessionsState() {
        val prefs = requireContext().getSharedPreferences("buttons_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        professions.forEach { profession ->
            editor.putBoolean(profession.id.toString(), profession.isSelected)
        }
        editor.apply()
    }

    private fun loadProfessionsState() {
        val prefs = requireContext().getSharedPreferences("buttons_prefs", Context.MODE_PRIVATE)
        professions.forEach { profession ->
            profession.isSelected = prefs.getBoolean(profession.id.toString(), false)
        }
    }

    private fun clearSavedValues(prefs: SharedPreferences) {
        prefs.edit().clear().apply()
    }
}
