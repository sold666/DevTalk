package com.dev_talk.main.my_chats

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.main.structures.Chat
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class PersonalChatsAdapter(

    private val chats: List<Chat>,
    private val onChatClickListener: (chat: Chat, adapterPosition: Int) -> Unit
) : RecyclerView.Adapter<PersonalChatsAdapter.ChatItemViewHolder>(), Filterable {

    internal var filteredChats: List<Chat>

    init {
        filteredChats = chats
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_default_chat, parent, false)
    )

    override fun getItemCount() = filteredChats.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(filteredChats[position], onChatClickListener)
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: ConstraintLayout = itemView.findViewById(R.id.chat_layout)
        private val icon: CircleImageView = itemView.findViewById(R.id.chat_icon)
        private val chat: TextView = itemView.findViewById(R.id.chat_name)
        private val lastMessage: TextView = itemView.findViewById(R.id.last_message)


        fun bind(
            chat: Chat,
            listener: (chat: Chat, adapterPosition: Int) -> Unit
        ) {
            icon.setImageResource(chat.icon)
            this.chat.text = chat.name
            lastMessage.text = chat.lastMessage

            container.setOnClickListener { listener.invoke(chat, adapterPosition) }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val query = p0.toString()
                filteredChats = if (query.isEmpty()) {
                    chats
                } else {
                    val resultList = ArrayList<Chat>()
                    for (chat in chats) {
                        if (chat.name.lowercase(Locale.ROOT)
                                .contains(query.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(chat)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredChats
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(quary: CharSequence?, filterResults: FilterResults?) {
                filteredChats = filterResults!!.values as List<Chat>
                notifyDataSetChanged()
            }

        }
    }
}
