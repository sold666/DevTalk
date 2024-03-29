package com.dev_talk.auth.profession

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.R
import com.dev_talk.auth.AuthActivity
import com.dev_talk.auth.SelectionViewModel
import com.dev_talk.common.structures.ProfessionDto
import com.dev_talk.databinding.FragmentProfessionBinding
import com.dev_talk.dto.User
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.PROFESSIONS_LIMIT
import com.dev_talk.utils.USER_KEY
import com.dev_talk.utils.getProfessions

class ProfessionFragment : Fragment() {

    private lateinit var binding: FragmentProfessionBinding
    private lateinit var professionAdapter: ProfessionAdapter
    private lateinit var onProfessionsClickListener: (profession: ProfessionDto, adapterPosition: Int) -> Unit
    private lateinit var user: User
    private var professions: List<ProfessionDto> = getProfessions()
    private val viewModel: SelectionViewModel by activityViewModels()

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
            user =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelable(
                        USER_KEY,
                        User::class.java
                    )!!
                } else {
                    arguments?.getParcelable(USER_KEY)!!
                }
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
            viewModel.resetSelectedProfessionsCount()
            professions.forEach { it.isSelected = false }
        }

        binding.nextButton.setOnClickListener {
            val selectedProfessions = professions.filter { it.isSelected }.sortedBy { it.id }
            val bundle = Bundle().apply {
                putParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    ArrayList(selectedProfessions)
                )
                putParcelable(
                    USER_KEY,
                    user
                )
            }
            findNavController().navigate(R.id.action_professionFragment_to_tagsFragment, bundle)
        }

        onProfessionsClickListener = onProfessionsClickListener@{ profession, position ->
            if (profession.isSelected) {
                if (viewModel.selectedProfessionsCount.value!! > 0) {
                    viewModel.setSelectedProfessionsCount(viewModel.selectedProfessionsCount.value!! - 1)
                }
            } else {
                if (viewModel.selectedProfessionsCount.value!! == 3) {
                    Toast.makeText(
                        context,
                        context?.getString(R.string.professions_limit_message),
                        Toast.LENGTH_LONG,
                    ).show()
                    return@onProfessionsClickListener
                }
                viewModel.setSelectedProfessionsCount(viewModel.selectedProfessionsCount.value!! + 1)
            }
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
        viewModel.selectedProfessionsCount.observe(viewLifecycleOwner) { count ->
            binding.amountProfessions.text = String.format(
                getString(R.string.selected_amount),
                count,
                PROFESSIONS_LIMIT
            )
            binding.nextButton.isEnabled = count > 0
        }
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
        viewModel.resetSelectedProfessionsCount()
    }
}
