package com.dev_talk.auth.profession

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
        private val container: CardView = itemView.findViewById(R.id.profession_card)
        private val professionName: TextView = itemView.findViewById(R.id.profession_name)

        private val backgroundColor =
            itemView.context.getThemeColorRes(R.attr.background_color_primary)
        private val buttonColor =
            itemView.context.getThemeColorRes(R.attr.button_click_color_secondary)

        fun bind(
            profession: Profession,
            listener: (profession: Profession, adapterPosition: Int) -> Unit
        ) {

            professionName.text = profession.name
            val bgColor = if (profession.isSelected) {
                ContextCompat.getColor(itemView.context, buttonColor)
            } else {
                ContextCompat.getColor(itemView.context, backgroundColor)
            }
            container.setCardBackgroundColor(bgColor)

            container.setOnClickListener { listener.invoke(profession, adapterPosition) }
        }
    }
}
