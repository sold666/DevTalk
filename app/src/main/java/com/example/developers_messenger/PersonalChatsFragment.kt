package com.example.developers_messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.developers_messenger.databinding.FragmentPersonalChatsBinding
import com.example.developers_messenger.view_pager_2_adapters.PersonalChatsAdapterViewPager
import com.google.android.material.tabs.TabLayout


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PersonalChatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonalChatsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentPersonalChatsBinding? = null
    private val _binding: FragmentPersonalChatsBinding
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

        with(_binding) {
            val data = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY, Profession::class.java)!!
            } else {
                arguments?.getParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY)!!
            }
            chatsWithCategory.adapter = PersonalChatsAdapterViewPager(activity!!, professions.tabCount, data, getProfessionsNames())
            chatsWithCategory.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    professions.selectTab(professions.getTabAt(position))
                }
            })
            professions.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab) {
                    chatsWithCategory.currentItem = tab.position
                }
            })
        }
    }

    private fun getProfessionsNames() : ArrayList<String> {
        val listWithNames = ArrayList<String>()
        for (i in 0 until _binding.professions.tabCount) {
            listWithNames.add(_binding.professions.getTabAt(i)?.text.toString())
        }
        return listWithNames
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_personal_chats, container, false)
        binding = FragmentPersonalChatsBinding.inflate(inflater)
        return _binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PersonalChatsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PersonalChatsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
