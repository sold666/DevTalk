package com.example.developers_messenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ProfileChatsAdapter(
    private val professions: ArrayList<Chat>
): RecyclerView.Adapter<ProfileChatsAdapter.ChatItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        return ChatItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.profile_chat_item, null)
        )
    }

    override fun getItemCount(): Int {
        return professions.size
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(professions[position])
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profession: TextView = itemView.findViewById(R.id.profession)
        private val tags: TextView = itemView.findViewById(R.id.tags)

        fun bind(chat: Chat) {
            profession.text = chat.profession
            tags.text = getTagsText(chat)
        }

        private fun getTagsText(src: Chat) : String {
            var allTags: String = ""
            for ((index, tag) in src.tags.withIndex()) {
                allTags += tag
                if (index != src.tags.size - 1) {
                    allTags += ", "
                }

            }
            return allTags
        }
    }
}