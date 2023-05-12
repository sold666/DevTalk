package com.dev_talk.main.profile.edit

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.main.structures.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.min

class EditProfileLinkAdapter(val data: MutableList<Link>) :
    RecyclerView.Adapter<EditProfileLinkAdapter.LinkItemViewHolder>() {

    companion object {
        val links: Map<String, Int> = mapOf(
            "Github" to R.drawable.ic_github,
            "Gitlab" to R.drawable.ic_gitlab,
            "Linkedin" to R.drawable.ic_linked
        )
        val types: Map<String, LinkType> = mapOf(
            "Github" to LinkType.GITHUB,
            "Gitlab" to LinkType.GITLAB,
            "Linkedin" to LinkType.LINKEDIN
        )
        val baseUrl: Map<LinkType, String> = mapOf(
            LinkType.GITHUB to "https://github.com/",
            LinkType.GITLAB to "https://gitlab.com/",
            LinkType.LINKEDIN to "https://www.linkedin.com/in/"
        )

        private fun findType(type: LinkType): String {
            for ((key, value) in types) {
                if (value == type) {
                    return key
                }
            }
            return "Github"
        }

        private val linkTypes: MutableSet<String> = types.keys.toMutableSet()
    }

    private var isShownAddBtn = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LinkItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_link_profile_edit, parent, false),
            parent.context
        )

    override fun getItemCount() = min(data.size, 3)

    override fun onBindViewHolder(holder: LinkItemViewHolder, position: Int) {
        if (position == data.size - 1 && isShownAddBtn) {
            holder.bindInsertButton(data, this)
            return
        }
        holder.bind(data, position, this)
    }

    class LinkItemViewHolder(val view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        private val icon = view.findViewById<CircleImageView>(R.id.links)
        private val editBtn = view.findViewById<ImageView>(R.id.edit_btn)

        fun bind(data: MutableList<Link>, position: Int, adapter: EditProfileLinkAdapter) {
            val link: Link = data[position]
            editBtn.visibility = View.VISIBLE
            icon.setImageResource(link.icon)
            icon.setOnClickListener {
                setUpEditDialog(data, position, adapter)
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun setUpEditDialog(
            data: MutableList<Link>,
            position: Int,
            adapter: EditProfileLinkAdapter
        ) {
            val dialog = Dialog(context)
            dialog.apply {
                setContentView(R.layout.dialog_edit_link)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }
            val userLink: EditText = dialog.findViewById(R.id.user_link)
            val saveBtn: Button = dialog.findViewById(R.id.save_button)
            val deleteBtn: Button = dialog.findViewById(R.id.delete_button)
            saveBtn.setOnClickListener {
                val userLinkText = userLink.text.toString()
                GlobalScope.launch {
                    val currentLinkUrl =
                        baseUrl.getOrDefault(data[position].type, "https://github.com/")
                    val linkIsValid = isLinkValid(userLinkText, currentLinkUrl)
                    if (!linkIsValid) {
                        Toast.makeText(
                            context,
                            context.getText(R.string.data_is_not_correct),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        data[position].url = currentLinkUrl + userLinkText
                        dialog.dismiss()
                    }
                }

            }

            deleteBtn.setOnClickListener {
                linkTypes.add(findType(data[position].type))
                data.removeAt(position)
                if (!adapter.isShownAddBtn) {
                    data.add(Link(R.drawable.ic_add_new_chat_btn))
                    adapter.isShownAddBtn = true
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }

        private suspend fun isLinkValid(username: String, baseUrl: String) =
            withContext(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()

                val service = retrofit.create(LinkValidation::class.java)
                val call = service.getData(username)

                val response = call.execute()
                return@withContext response.isSuccessful
            }

        fun bindInsertButton(data: MutableList<Link>, adapter: EditProfileLinkAdapter) {
            icon.setImageResource(data[data.size - 1].icon)
            icon.setOnClickListener {
                setUpAddLinkDialog(data, adapter)
            }
            editBtn.visibility = View.GONE
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun setUpAddLinkDialog(data: MutableList<Link>, adapter: EditProfileLinkAdapter) {
            val dialog = Dialog(context)
            dialog.apply {
                setContentView(R.layout.dialog_add_link)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }
            val userLink: EditText = dialog.findViewById(R.id.user_link)
            val linkType: Spinner = dialog.findViewById(R.id.link_type)
            setupSpinner(linkType)
            val saveBtn: Button = dialog.findViewById(R.id.save_button)
            saveBtn.setOnClickListener {
                val userLinkText = userLink.text.toString()
                val linkTypeText = linkType.selectedItem.toString()
                val activity = itemView.context as Activity

                GlobalScope.launch {
                    val linkIsValid = isLinkValid(
                        userLinkText,
                        baseUrl.getOrDefault(types[linkTypeText], "https://github.com/")
                    )
                    if (!linkIsValid) {
                        Toast.makeText(
                            context,
                            context.getText(R.string.data_is_not_correct),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val currentIcon = links.getOrDefault(linkTypeText, R.drawable.ic_leave)
                        linkTypes.remove(linkTypeText)
                        dialog.dismiss()
                        if (adapter.itemCount < 4) {
                            if (adapter.itemCount == 3 && adapter.isShownAddBtn) {
                                data.removeAt(data.size - 1)
                                adapter.isShownAddBtn = false
                            }
                            data.add(
                                0,
                                Link(
                                    currentIcon,
                                    userLinkText,
                                    types.getOrDefault(linkTypeText, LinkType.GITHUB)
                                )
                            )
                            activity.runOnUiThread {
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

            }
        }

        private fun setupSpinner(spinner: Spinner) {
            val spinnerAdapter: ArrayAdapter<String> =
                ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
                    linkTypes.toMutableList()
                )
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerAdapter
        }
    }
}
