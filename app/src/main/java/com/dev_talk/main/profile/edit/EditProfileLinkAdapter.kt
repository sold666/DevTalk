package com.dev_talk.main.profile.edit

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.main.structures.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.min

class EditProfileLinkAdapter(val data: MutableList<Link>) :
    RecyclerView.Adapter<EditProfileLinkAdapter.LinkItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LinkItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_link_profile, parent, false),
            parent.context
        )

    override fun getItemCount() = min(data.size, 3)

    override fun onBindViewHolder(holder: LinkItemViewHolder, position: Int) {
        if (position == data.size - 1) {
            holder.bindInsertButton(data, this)
            return
        }
        holder.bind(data[position])
    }

    class LinkItemViewHolder(val view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        private val icon = view.findViewById<CircleImageView>(R.id.links)

        fun bind(link: Link) {
            icon.setImageResource(link.icon)
        }

        private fun isLinkValid(url: String): Boolean {
            return true
        }

        private fun isLinkTypeValid(linkType: String): Boolean {
            return true
        }

        fun bindInsertButton(data: MutableList<Link>, adapter: EditProfileLinkAdapter) {
            icon.setImageResource(data[data.size - 1].icon)
            icon.setOnClickListener {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.dialog_add_link)
                dialog.show()
                val userLink: EditText = dialog.findViewById(R.id.user_link)
                val linkType: Spinner = dialog.findViewById(R.id.link_type)
                val saveBtn: Button = dialog.findViewById(R.id.save_button)
                var currentIcon: Int = R.drawable.ic_leave
                saveBtn.setOnClickListener {
                    val userLinkText = userLink.text.toString()
                    val linkTypeText = linkType.selectedItem.toString()
                    if (!isLinkValid(userLinkText) || !isLinkTypeValid(linkTypeText)) {
                        Toast.makeText(context, "Please, enter the data", Toast.LENGTH_SHORT).show()
                    } else {
                        currentIcon = when (linkTypeText) {
                            "Github" -> R.drawable.ic_person
                            "Gitlab" -> R.drawable.ic_my_chats
                            "Linkedin" -> R.drawable.ic_notification
                            else -> R.drawable.ic_leave
                        }
                        dialog.dismiss()
                    }
                    if (adapter.itemCount < 4) {
                        if (adapter.itemCount == 3) {
                            data.removeAt(data.size - 1)
                            adapter.notifyItemRemoved(data.size)
                        }
                        data.add(0, Link(currentIcon))
                        adapter.notifyItemInserted(0)
                    }
                }
            }
        }
    }
}
