package com.dev_talk.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.main.R
import com.dev_talk.main.structures.Chat
import de.hdodenhof.circleimageview.CircleImageView

class ProfileChatsItemAdapter(
    private val chats: List<Chat>
) : RecyclerView.Adapter<ProfileChatsItemAdapter.ChatItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.profile_chat_child_item, null)
    )

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: CircleImageView = itemView.findViewById(R.id.tag_image)
        private val tags: TextView = itemView.findViewById(R.id.tag)

        fun bind(chat: Chat) {
            tags.text = chat.tags
            icon.setImageResource(chat.icon)
        }
    }
}
