package com.dev_talk.main.profile.edit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.main.structures.Header
import com.dev_talk.main.structures.Item
import com.dev_talk.main.structures.ProfileData
import de.hdodenhof.circleimageview.CircleImageView

class EditProfileChatsAdapter(
    private val data: MutableList<ProfileData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_profile_chat_parent, null)
            )
            else -> ItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_edit_profile_chat_child, null)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is Header -> 0
            is Item -> 1
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                val currentHolder = holder as HeaderViewHolder
                currentHolder.bind(data[position] as Header)
            }
            else -> {
                val currentHolder = holder as ItemViewHolder
                currentHolder.bind(data, position, this)
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: CircleImageView = itemView.findViewById(R.id.tag_image)
        private val tags: TextView = itemView.findViewById(R.id.tag)
        private val deleteBtn: ImageButton = itemView.findViewById(R.id.delete_btn)

        fun bind(data: MutableList<ProfileData>, position: Int, adapter: EditProfileChatsAdapter) {
            deleteBtn.setOnClickListener {
                data.removeAt(position)
                //adapter.notifyItemRemoved(position)
                //TODO("Replace adapter.notifyDataSetChanged() with adapter.notifyItemRemoved(position)")
                adapter.notifyDataSetChanged()
            }
            tags.text = (data[position] as Item).chat.tags
            icon.setImageResource((data[position] as Item).chat.icon)
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profession: TextView = itemView.findViewById(R.id.profession)

        fun bind(header: Header) {
            profession.text = header.title
        }
    }
}
