package com.dev_talk.auth

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.structures.Profession

class ProfessionAdapter(

    private val professions: ArrayList<Profession>,
    private val selectedProfessions: ArrayList<Profession>,
    private val buttonNext: Button
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

        val typedValueForBackground = TypedValue()
        val theme = holder.itemView.context.theme
        theme.resolveAttribute(R.attr.background_color_primary, typedValueForBackground, true)
        val typedValueForButton = TypedValue()
        theme.resolveAttribute(R.attr.button_click_color_secondary, typedValueForButton, true)

        holder.itemView.setOnClickListener {
            holder.itemView.findViewById<CardView>(R.id.profession_card).setCardBackgroundColor(
                if (selectedProfessions.contains(profession)) {
                    selectedProfessions.remove(profession)
                    ContextCompat.getColor(
                        holder.itemView.context,
                        typedValueForBackground.resourceId
                    )
                } else {
                    selectedProfessions.add(profession)
                    ContextCompat.getColor(holder.itemView.context, typedValueForButton.resourceId)
                }
            )
            buttonNext.isEnabled = selectedProfessions.isNotEmpty()
        }
        holder.itemView.findViewById<CardView>(R.id.profession_card).setCardBackgroundColor(
            if (selectedProfessions.contains(profession)) ContextCompat.getColor(
                holder.itemView.context,
                typedValueForButton.resourceId
            ) else ContextCompat.getColor(
                holder.itemView.context,
                typedValueForBackground.resourceId
            )
        )
    }

    fun getSelectedProfessions(): ArrayList<Profession> {
        return selectedProfessions
    }

    class ProfessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val professionName: TextView = itemView.findViewById(R.id.profession_name)

        fun bind(profession: Profession) {
            professionName.text = profession.name
        }
    }
}
