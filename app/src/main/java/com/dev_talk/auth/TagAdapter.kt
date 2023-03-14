package com.dev_talk.auth

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R

class TagAdapter(

    private val tags: ArrayList<String>,
    private val selectedTags: ArrayList<String>
) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = tags[position]
        holder.bind(tag)

        val typedValueForBackground = TypedValue()
        val theme = holder.itemView.context.theme
        theme.resolveAttribute(R.attr.background_color_primary, typedValueForBackground, true)
        val typedValueForButton = TypedValue()
        theme.resolveAttribute(R.attr.button_click_color_secondary, typedValueForButton, true)

        holder.itemView.setOnClickListener {
            holder.itemView.findViewById<CardView>(R.id.tag_card).setCardBackgroundColor(
                if (selectedTags.contains(tag)) {
                    selectedTags.remove(tag)
                    ContextCompat.getColor(
                        holder.itemView.context,
                        typedValueForBackground.resourceId
                    )
                } else {
                    selectedTags.add(tag)
                    ContextCompat.getColor(holder.itemView.context, typedValueForButton.resourceId)
                }
            )
        }
        holder.itemView.findViewById<CardView>(R.id.tag_card).setCardBackgroundColor(
            if (selectedTags.contains(tag)) ContextCompat.getColor(
                holder.itemView.context,
                typedValueForButton.resourceId
            ) else ContextCompat.getColor(
                holder.itemView.context,
                typedValueForBackground.resourceId
            )
        )
    }

    fun getSelectedTags(): ArrayList<String> {
        return selectedTags
    }

    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tagName: TextView = itemView.findViewById(R.id.tag_name)

        fun bind(tag: String) {
            tagName.text = tag
        }
    }
}
