package com.dev_talk.main.my_chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dev_talk.main.databinding.FragmentPersonalChatsBinding
import com.dev_talk.main.structures.Profession
import com.google.android.material.tabs.TabLayout

private const val DEFAULT_LIST_PROFESSIONS_KEY = "professions"
class PersonalChatsFragment : Fragment() {
    private var binding: FragmentPersonalChatsBinding? = null
    private val _binding: FragmentPersonalChatsBinding
        get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalChatsBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(_binding) {
            val data = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY, Profession::class.java)!!
            } else {
                arguments?.getParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY)!!
            }
            with (chatsWithCategory) {
                adapter = PersonalChatsAdapterViewPager(requireActivity(), professions.tabCount, data, getProfessionsNames())
                registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        professions.selectTab(professions.getTabAt(position))
                    }
                })
                professions.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab?) {}

                    override fun onTabUnselected(tab: TabLayout.Tab?) {}

                    override fun onTabSelected(tab: TabLayout.Tab) {
                        currentItem = tab.position
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(professions: ArrayList<Profession>) =
            PersonalChatsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(DEFAULT_LIST_PROFESSIONS_KEY, professions)
                }
            }
    }

    private fun getProfessionsNames() : List<String> {
        val listWithNames = ArrayList<String>()
        with(_binding.professions) {
            for (i in 0 until tabCount) {
                listWithNames.add(getTabAt(i)?.text.toString())
            }
        }
        return listWithNames
    }
}
