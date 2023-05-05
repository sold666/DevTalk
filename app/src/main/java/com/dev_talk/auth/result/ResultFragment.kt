package com.dev_talk.auth.result

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_talk.R
import com.dev_talk.auth.structures.Profession
import com.dev_talk.auth.structures.Tag
import com.dev_talk.databinding.FragmentResultBinding
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.LIST_SELECTED_TAGS_KEY

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private lateinit var selectedTags: List<Tag>
    private lateinit var selectedProfessions: List<Profession>
    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.progressBar
        progressText = binding.progressText
        initListeners()
        with(binding) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                selectedTags = arguments?.getParcelableArrayList(
                    LIST_SELECTED_TAGS_KEY,
                    Tag::class.java
                )!!
                selectedProfessions = arguments?.getParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    Profession::class.java
                )!!
            } else {
                selectedTags = arguments?.getParcelableArrayList(LIST_SELECTED_TAGS_KEY)!!
                selectedProfessions =
                    arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
            }
            val listData = generateMapFromArrays()
            val titleList = selectedProfessions.map { it.name }
            val resultAdapter = ResultAdapter(resultList.context, listData, titleList)
            resultList.setAdapter(resultAdapter)
            for (i in listData.keys.indices) {
                resultList.expandGroup(i)
            }
        }
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener { findNavController().popBackStack() }

        binding.nextButton.setOnClickListener {
            binding.resultList.visibility = View.GONE
            binding.backButton.visibility = View.GONE
            binding.nextButton.visibility = View.GONE
            binding.resultText.visibility = View.GONE
            binding.text.visibility = View.GONE
            progressBar.isVisible = true
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val progress = ((5000 - millisUntilFinished) / 50).toInt()
                    progressBar.progress = progress
                    progressText.text = context?.getString(R.string.processing_data)
                }

                override fun onFinish() {
                    progressText.text = context?.getString(R.string.is_ready_text)
                    progressBar.isVisible = false
                    findNavController().navigate(R.id.action_resultFragment_to_mainActivity)
                    activity?.finish()
                }
            }.start()
        }
    }

    private fun generateMapFromArrays(): Map<String, List<String>> {
        val expandableListMap = HashMap<String, List<String>>()
        for (profession in selectedProfessions) {
            val tagsNamesList = profession.tags.filter { selectedTags.contains(it) }.map { it.name }
            expandableListMap[profession.name] = tagsNamesList
        }
        return expandableListMap
    }
}
