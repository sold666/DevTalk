package com.dev_talk.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.structures.Profession
import com.dev_talk.utils.getThemeColorRes

class ProfessionAdapter(

    val professions: ArrayList<Profession>,
    private val onProfessionsChanged: () -> Unit
) : RecyclerView.Adapter<ProfessionAdapter.ProfessionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessionViewHolder {
        return ProfessionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profession, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return professions.size
    }

    override fun onBindViewHolder(holder: ProfessionViewHolder, position: Int) {
        val profession = professions[position]
        holder.bind(profession)

        val backgroundColor = holder.itemView.context.getThemeColorRes(R.attr.background_color_primary)
        val buttonColor = holder.itemView.context.getThemeColorRes(R.attr.button_click_color_secondary)

        holder.itemView.setOnClickListener {
            profession.isSelected = !profession.isSelected
            holder.itemView.findViewById<CardView>(R.id.profession_card).setCardBackgroundColor(
                if (profession.isSelected) {
                    ContextCompat.getColor(holder.itemView.context, buttonColor)
                } else {
                    ContextCompat.getColor(
                        holder.itemView.context,
                        backgroundColor
                    )
                }
            )
            onProfessionsChanged.invoke()
        }
    }

    fun List<Profession>.getSelectedProfessions(): List<Profession> {
        return this.filter { it.isSelected }
    }

    class ProfessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val professionName: TextView = itemView.findViewById(R.id.profession_name)

        fun bind(profession: Profession) {
            professionName.text = profession.name
        }
    }
}
