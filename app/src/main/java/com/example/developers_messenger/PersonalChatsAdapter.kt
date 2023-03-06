package com.example.developers_messenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class PersonalChatsAdapter(
    private val chats: Chat
): RecyclerView.Adapter<PersonalChatsAdapter.ChatItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {

        return ChatItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.default_chat_item, null)
        )
    }

    override fun getItemCount(): Int {
        return chats.tags.size
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(chats, position)
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.chat_icon)
        private val chat: TextView = itemView.findViewById(R.id.chat_name)

        fun bind(src: Chat, index: Int) {
            icon.setImageResource(src.icon)
            chat.text = src.tags[index]
        }
    }
}