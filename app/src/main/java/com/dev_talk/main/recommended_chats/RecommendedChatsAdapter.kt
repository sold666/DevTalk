package com.dev_talk.main.recommended_chats

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.main.R
import com.dev_talk.main.structures.Chat
import java.util.*
import kotlin.collections.ArrayList

class RecommendedChatsAdapter(
    private var chats: List<Chat>
) : RecyclerView.Adapter<RecommendedChatsAdapter.ChatItemViewHolder>(), Filterable {

    internal var filteredChats: List<Chat>

    init {
        filteredChats = chats
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.default_chat_item, null)
    )

    override fun getItemCount() = filteredChats.size

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(filteredChats[position])
    }

    class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.chat_icon)
        private val chat: TextView = itemView.findViewById(R.id.chat_name)

        fun bind(src: Chat) {
            icon.setImageResource(src.icon)
            chat.text = src.tags
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
                        if (chat.tags.lowercase(Locale.ROOT)
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
