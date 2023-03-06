package com.dev_talk.recycler_view_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.structures.Chat
import com.dev_talk.R

class RecommendedChatsAdapter(
private val chats: ArrayList<Chat>
): RecyclerView.Adapter<RecommendedChatsAdapter.ChatItemViewHolder>()  {
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

        fun bind(src: Chat) {
            icon.setImageResource(src.icon)
            chat.text = src.tags
        }
    }
}
