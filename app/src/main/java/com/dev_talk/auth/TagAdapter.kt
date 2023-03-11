package com.dev_talk.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.main.R

class TagAdapter(
    private val tags: ArrayList<String>,
    private val selectedTags: ArrayList<String>
) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tag, null)
        )
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = tags[position]
        holder.bind(tag)
        holder.tagCheckbox.isChecked = selectedTags.contains(tag)

        holder.tagCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedTags.add(tag)
            } else {
                selectedTags.remove(tag)
            }
        }
    }

    fun getSelectedTags(): ArrayList<String> {
        return selectedTags
    }

    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tagName: TextView = itemView.findViewById(R.id.tag_name)
        val tagCheckbox: CheckBox = itemView.findViewById(R.id.tag_checkbox)

        fun bind(tag: String) {
            tagName.text = tag
        }
    }
}
