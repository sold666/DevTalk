package com.dev_talk.main.profile.information

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.main.structures.Link
import de.hdodenhof.circleimageview.CircleImageView

class ProfileLinkAdapter(val data: List<Link>) :
    RecyclerView.Adapter<ProfileLinkAdapter.LinkItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LinkItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_link_profile, parent, false),
            parent.context
        )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: LinkItemViewHolder, position: Int) {
        if (position == data.size - 1 && data.size == 1 && data[position].icon == getInfoLinkImage()) {
            holder.bindInfoButton(data[position])
            return
        }
        holder.bind(data[position])
    }

    class LinkItemViewHolder(val view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        private val icon = view.findViewById<CircleImageView>(R.id.links)

        fun bind(link: Link) {
            icon.setImageResource(link.icon)
            icon.setOnClickListener {
                val url = link.url
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                context.startActivity(i)
            }
        }

        fun bindInfoButton(link: Link) {
            icon.setImageResource(link.icon)
        }
    }

    private fun getInfoLinkImage() = R.drawable.ic_links
}
