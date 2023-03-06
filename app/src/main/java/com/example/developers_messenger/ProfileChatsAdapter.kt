package com.example.developers_messenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileChatsAdapter(
    private val professions: ArrayList<Profession>
): RecyclerView.Adapter<ProfileChatsAdapter.ChatItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        return ChatItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.profile_chat_item, null)
        )
    }

    override fun getItemCount() = professions.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(professions[position])
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profession: TextView = itemView.findViewById(R.id.profession)
        private val tags: TextView = itemView.findViewById(R.id.tags)

        fun bind(chat: Profession) {
            profession.text = chat.profession
                     tags.text = getChatsText(chat)
        }

        private fun getChatsText(src: Profession) : String {
            var allTags: String = ""
            for ((index, chat) in src.chats.withIndex()) {
                allTags += chat.tags
                if (index != src.chats.size - 1) {
                    allTags += ", "
                }
            }
            return allTags
        }
    }
}
