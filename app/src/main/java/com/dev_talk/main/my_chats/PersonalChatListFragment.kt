package com.dev_talk.main.my_chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.databinding.FragmentPersonalChatListBinding
import com.dev_talk.main.common.Divider.Companion.getRecyclerViewDivider
import com.dev_talk.main.structures.Profession

private const val PROFESSION_KEY = "Current profession"

class PersonalChatListFragment : Fragment() {
    private lateinit var binding: FragmentPersonalChatListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalChatListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.listWithMyChats) {
            val data =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    arguments?.getParcelable(PROFESSION_KEY, Profession::class.java)!!
                } else {
                    arguments?.getParcelable(PROFESSION_KEY)!!
                }
            adapter = PersonalChatsAdapter(data.chats)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(getRecyclerViewDivider(context))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(profession: Profession) =
            PersonalChatListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PROFESSION_KEY, profession)
                }
            }
    }
}
