package com.dev_talk.main.edit_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev_talk.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.databinding.FragmentEditProfileBinding
import com.dev_talk.main.profile.ProfileLinkAdapter
import com.dev_talk.main.structures.Link

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setUpLinks(socialNetwork)
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

    private fun getLinks() : ArrayList<Link> {
        return arrayListOf(
            Link(R.drawable.ic_person),
            Link(R.drawable.ic_person)
        )
    }

}
