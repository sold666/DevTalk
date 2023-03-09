package com.dev_talk.recycler_view_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.structures.Chat

class PersonalChatsAdapter(
    private val chats: List<Chat>
): RecyclerView.Adapter<PersonalChatsAdapter.ChatItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        return ChatItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.default_chat_item, null)
        )
    }

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.chat_icon)
        private val chat: TextView = itemView.findViewById(R.id.chat_name)

        fun bind(chat: Chat) {
            icon.setImageResource(chat.icon)
            this.chat.text = chat.tags
        }
    }
}
