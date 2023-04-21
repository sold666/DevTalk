package com.dev_talk.auth.tag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.auth.structures.Tag
import com.dev_talk.utils.getThemeColorRes

class TagAdapter(

    private val onTagsClickListener: (tag: Tag, adapterPosition: Int) -> Unit
) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    private var tags = arrayListOf<Tag>()

    fun setData(data: List<Tag>) {
        tags.clear()
        tags.addAll(data)
        notifyDataSetChanged()
    }

    fun setData(data: List<Tag>, position: Int) {
        tags.clear()
        tags.addAll(data)
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        )
    }

    override fun getItemCount() = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position], onTagsClickListener)
    }

    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: CardView = itemView.findViewById(R.id.tag_card)
        private val tagName: TextView = itemView.findViewById(R.id.tag_name)

        private val textColorIsSelected =
            itemView.context.getThemeColorRes(R.attr.recycler_stroke_and_text_button_color)
        private val textColor =
            itemView.context.getThemeColorRes(R.attr.text_color)

        fun bind(
            tag: Tag,
            listener: (tag: Tag, adapterPosition: Int) -> Unit
        ) {
            tagName.text = tag.name
            val textColor = if (tag.isSelected) {
                ContextCompat.getColor(itemView.context, textColorIsSelected)
            } else {
                ContextCompat.getColor(itemView.context, textColor)
            }
            tagName.setTextColor(textColor)

            container.background = if (tag.isSelected) {
                ContextCompat.getDrawable(itemView.context, R.drawable.selectable_recycler_button)
            } else {
                ContextCompat.getDrawable(itemView.context, R.drawable.unselectable_recycler_button)
            }
            container.setOnClickListener {
                listener.invoke(tag, adapterPosition)
            }
        }
    }
}
