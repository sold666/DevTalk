package com.example.developers_messenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonalChatsAdapter(
    private val profession: Profession
): RecyclerView.Adapter<PersonalChatsAdapter.ChatItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        return ChatItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.default_chat_item, null)
        )
    }

    override fun getItemCount() = profession.chats.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(src = profession, index = position)
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.chat_icon)
        private val chat: TextView = itemView.findViewById(R.id.chat_name)

        fun bind(src: Profession, index: Int) {
            icon.setImageResource(src.chats[index].icon)
            chat.text = src.chats[index].tags
        }
    }
}
