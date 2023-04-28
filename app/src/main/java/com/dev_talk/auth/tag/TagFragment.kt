package com.dev_talk.auth.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.R
import com.dev_talk.auth.SelectionViewModel
import com.dev_talk.auth.structures.Profession
import com.dev_talk.auth.structures.Tag
import com.dev_talk.databinding.FragmentTagBinding
import com.dev_talk.utils.LIST_SELECTED_PROFESSIONS_KEY
import com.dev_talk.utils.LIST_SELECTED_TAGS_KEY

class TagFragment : Fragment() {

    private lateinit var binding: FragmentTagBinding
    private lateinit var tagAdapter: TagAdapter
    private lateinit var onTagsClickListener: (tag: Tag, adapterPosition: Int) -> Unit
    private lateinit var tags: List<Tag>
    private lateinit var selectedProfessions: List<Profession>
    private val viewModel: SelectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTagBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        with(binding) {
            selectedProfessions =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelableArrayList(
                        LIST_SELECTED_PROFESSIONS_KEY,
                        Profession::class.java
                    )!!
                } else {
                    arguments?.getParcelableArrayList(LIST_SELECTED_PROFESSIONS_KEY)!!
                }
            tags = ArrayList(selectedProfessions.flatMap(Profession::tags))
            nextButton.isEnabled = false
            tagAdapter = TagAdapter(onTagsClickListener)
            tagList.adapter = tagAdapter
            tagList.itemAnimator = null
            tagList.layoutManager = LinearLayoutManager(tagList.context)
        }
        tagAdapter.setData(tags)
        updateNextButtonStatus()
    }

    private fun initListeners() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
            tags.forEach { it.isSelected = false }
            viewModel.resetSelectedTagsCount()
        }

        binding.nextButton.setOnClickListener {
            val selectedTags = tags.filter { it.isSelected }.sortedBy { it.id }
            val bundle = Bundle().apply {
                putParcelableArrayList(
                    LIST_SELECTED_TAGS_KEY,
                    ArrayList(selectedTags)
                )
                putParcelableArrayList(
                    LIST_SELECTED_PROFESSIONS_KEY,
                    ArrayList(selectedProfessions)
                )
            }
            findNavController().navigate(R.id.action_tagsFragment_to_resultFragment, bundle)
        }

        onTagsClickListener = { tag, position ->
            if (tag.isSelected) {
                if (viewModel.selectedTagsCount.value!! > 0) {
                    viewModel.setSelectedTagsCount(viewModel.selectedTagsCount.value!! - 1)
                }
            } else {
                viewModel.setSelectedTagsCount(viewModel.selectedTagsCount.value!! + 1)
            }
            tags.forEach {
                if (it.id == tag.id) {
                    it.isSelected = !it.isSelected
                }
            }
            tagAdapter.setData(tags, position)
            updateNextButtonStatus()
        }
    }

    private fun updateNextButtonStatus() {
        viewModel.selectedTagsCount.observe(viewLifecycleOwner) { count ->
            binding.amountTags.text = String.format(
                getString(R.string.selected_amount),
                count,
                tags.size
            )
            binding.nextButton.isEnabled = count > 0
        }
    }
}
