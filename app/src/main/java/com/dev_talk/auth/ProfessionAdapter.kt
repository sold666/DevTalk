package com.dev_talk.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.main.R
import com.dev_talk.structures.Profession

class ProfessionAdapter(
    private val professions: ArrayList<Profession>,
    private val selectedProfessions: ArrayList<Profession>
) : RecyclerView.Adapter<ProfessionAdapter.ProfessionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessionViewHolder {
        return ProfessionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profession, null)
        )
    }

    override fun getItemCount(): Int {
        return professions.size
    }

    override fun onBindViewHolder(holder: ProfessionViewHolder, position: Int) {
        val profession = professions[position]
        holder.bind(profession)
        holder.professionCheckbox.isChecked = selectedProfessions.contains(profession)

        holder.professionCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedProfessions.add(profession)
            } else {
                selectedProfessions.remove(profession)
            }
        }
    }

    fun getSelectedProfessions(): ArrayList<Profession> {
        return selectedProfessions
    }

    class ProfessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val professionName: TextView = itemView.findViewById(R.id.profession_name)
        val professionCheckbox: CheckBox = itemView.findViewById(R.id.profession_checkbox)

        fun bind(profession: Profession) {
            professionName.text = profession.name
        }
    }
}
