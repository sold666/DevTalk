package com.dev_talk.main.my_chats

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.main.structures.Chat

class PersonalChatsAdapter(

    private val chats: List<Chat>,
    private val onChatClickListener: (chat: Chat, adapterPosition: Int) -> Unit
) : RecyclerView.Adapter<PersonalChatsAdapter.ChatItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_default_chat, null)
    )

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(chats[position], onChatClickListener)
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: ConstraintLayout = itemView.findViewById(R.id.chat_layout)
        private val icon: ImageView = itemView.findViewById(R.id.chat_icon)
        private val chat: TextView = itemView.findViewById(R.id.chat_name)

        fun bind(chat: Chat,
                 listener: (chat: Chat, adapterPosition: Int) -> Unit) {
            icon.setImageResource(chat.icon)
            this.chat.text = chat.tags

            container.setOnClickListener { listener.invoke(chat, adapterPosition) }
        }
    }
}
