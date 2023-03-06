package com.example.developers_messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.developers_messenger.databinding.FragmentRecommendedChatsBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecommendedChatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecommendedChatsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentRecommendedChatsBinding? = null
    private val _binding: FragmentRecommendedChatsBinding
        get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(_binding) {
            val data = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(RECOMMENDED_LIST_PROFESSIONS_KEY, RecommendedChat::class.java)!!
            } else {
                arguments?.getParcelableArrayList(RECOMMENDED_LIST_PROFESSIONS_KEY)!!
            }
            recommendedChats.adapter = RecommendedChatsAdapter(data)
            recommendedChats.layoutManager = LinearLayoutManager(recommendedChats.context)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendedChatsBinding.inflate(inflater)
        return _binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_recommended_chats, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecommendedChatsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecommendedChatsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}