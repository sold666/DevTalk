package com.dev_talk.auth.tag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.structures.Tag
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

        private val backgroundColor =
            itemView.context.getThemeColorRes(R.attr.background_color_primary)
        private val buttonColor =
            itemView.context.getThemeColorRes(R.attr.button_click_color_secondary)

        fun bind(
            tag: Tag,
            listener: (tag: Tag, adapterPosition: Int) -> Unit
        ) {

            tagName.text = tag.name
            val bgColor = if (tag.isSelected) {
                ContextCompat.getColor(itemView.context, buttonColor)
            } else {
                ContextCompat.getColor(itemView.context, backgroundColor)
            }
            container.setCardBackgroundColor(bgColor)

            container.setOnClickListener { listener.invoke(tag, adapterPosition) }
        }
    }
}
