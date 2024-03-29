package com.dev_talk.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.chat.structures.ChatParticipant
import com.dev_talk.databinding.ItemChatParticipantBinding
import de.hdodenhof.circleimageview.CircleImageView

class ChatParticipantAdapter(
    private var participants: List<ChatParticipant>
) : RecyclerView.Adapter<ChatParticipantAdapter.ChatParticipantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatParticipantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatParticipantBinding.inflate(inflater, parent, false)
        return ChatParticipantViewHolder(binding)
    }

    override fun getItemCount(): Int = participants.size

    override fun onBindViewHolder(holder: ChatParticipantViewHolder, position: Int) {
        holder.bind(participants[position])
    }

    class ChatParticipantViewHolder(
        binding: ItemChatParticipantBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val icon: CircleImageView = binding.iconParticipant
        private val name: TextView = binding.nameParticipant
        private val status: TextView = binding.statusParticipant

        fun bind(participant: ChatParticipant) {
            icon.setImageResource(participant.icon)
            name.text = participant.name
            status.text = participant.status
        }
    }
}
