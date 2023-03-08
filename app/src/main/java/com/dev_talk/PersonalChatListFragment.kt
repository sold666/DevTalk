package com.dev_talk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.databinding.FragmentPersonalChatListBinding
import com.dev_talk.recycler_view_adapters.PersonalChatsAdapter
import com.dev_talk.structures.Profession

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PersonalChatListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonalChatListFragment(private val profession: Profession) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentPersonalChatListBinding? = null
    private val _binding: FragmentPersonalChatListBinding
        get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(_binding.listWithMyChats) {
            adapter = PersonalChatsAdapter(profession.chats)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalChatListBinding.inflate(inflater)
        return _binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_personal_chat_list, container, false)
    }
}
