package com.dev_talk.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.main.R
import com.dev_talk.main.structures.Profession

class ProfileChatsAdapter(
    private val professions: List<Profession>
) : RecyclerView.Adapter<ProfileChatsAdapter.ChatItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.profile_chat_parent_item, null)
    )

    override fun getItemCount() = professions.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(professions[position])
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nestedRecyclerView: RecyclerView = itemView.findViewById(R.id.tags_list)
        private val profession: TextView = itemView.findViewById(R.id.profession)

        fun bind(chat: Profession) {
            profession.text = chat.profession
            profession.text = chat.profession
            nestedRecyclerView.layoutManager = GridLayoutManager(itemView.context, 2)
            nestedRecyclerView.setHasFixedSize(true)
            nestedRecyclerView.adapter = ProfileChatsItemAdapter(chat.chats)
        }
    }
}
