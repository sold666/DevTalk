package com.dev_talk.main.my_chats

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_talk.main.databinding.FragmentPersonalChatListBinding
import com.dev_talk.main.structures.Profession

private const val PROFESSION_KEY = "Current profession"

class PersonalChatListFragment : Fragment() {
    private lateinit var binding: FragmentPersonalChatListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.listWithMyChats) {
            val data = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
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

    private fun getRecyclerViewDivider(context: Context): DividerItemDecoration {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(ContextCompat.getDrawable(context, com.dev_talk.main.R.drawable.divider)!!)
        return decoration
    }
}