package com.dev_talk.auth.profession

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev_talk.R
import com.dev_talk.auth.structures.Profession
import com.dev_talk.utils.getThemeColorRes
import com.google.android.material.card.MaterialCardView

class ProfessionAdapter(

    private val onProfessionsClickListener: (profession: Profession, adapterPosition: Int) -> Unit
) : RecyclerView.Adapter<ProfessionAdapter.ProfessionViewHolder>() {

    private var professions = arrayListOf<Profession>()

    fun setData(data: List<Profession>) {
        professions.clear()
        professions.addAll(data)
        notifyDataSetChanged()
    }

    fun setData(data: List<Profession>, position: Int) {
        professions.clear()
        professions.addAll(data)
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessionViewHolder {
        return ProfessionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_profession, parent, false)
        )
    }

    override fun getItemCount() = professions.size

    override fun onBindViewHolder(holder: ProfessionViewHolder, position: Int) {
        holder.bind(professions[position], onProfessionsClickListener)
    }

    class ProfessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: MaterialCardView = itemView.findViewById(R.id.profession_card)
        private val professionName: TextView = itemView.findViewById(R.id.profession_name)

        private val textColorIsSelected =
            itemView.context.getThemeColorRes(R.attr.recycler_stroke_and_text_button_color)
        private val textColor =
            itemView.context.getThemeColorRes(R.attr.text_color)

        fun bind(
            profession: Profession,
            listener: (profession: Profession, adapterPosition: Int) -> Unit
        ) {
            professionName.text = profession.name
            val textColor = if (profession.isSelected) {
                ContextCompat.getColor(itemView.context, textColorIsSelected)
            } else {
                ContextCompat.getColor(itemView.context, textColor)
            }
            professionName.setTextColor(textColor)

            container.background = if (profession.isSelected) {
                ContextCompat.getDrawable(itemView.context, R.drawable.selectable_recycler_button)
            } else {
                ContextCompat.getDrawable(itemView.context, R.drawable.unselectable_recycler_button)
            }
            container.setOnClickListener {
                listener.invoke(profession, adapterPosition)
            }
        }
    }
}
