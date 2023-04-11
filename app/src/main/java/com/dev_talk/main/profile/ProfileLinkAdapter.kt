package com.dev_talk.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.main.structures.Link
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.min

class ProfileLinkAdapter(val data: List<Link>): RecyclerView.Adapter<ProfileLinkAdapter.LinkItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LinkItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_link_profile, parent, false)
        )

    override fun getItemCount() = min(data.size + 1, 4)

    override fun onBindViewHolder(holder: LinkItemViewHolder, position: Int) {
        if (position == data.size) {
            holder.bind(Link(R.drawable.ic_add_new_chat_btn))
            return
        }
        holder.bind(data[position])
    }

    class LinkItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val icon = view.findViewById<CircleImageView>(R.id.links)

        fun bind(link: Link) {
             icon.setImageResource(link.icon)
        }
    }
}
