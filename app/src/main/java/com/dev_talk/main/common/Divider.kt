package com.dev_talk.main.common

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.dev_talk.R

class Divider {
    companion object {
        fun getRecyclerViewDivider(context: Context): DividerItemDecoration {
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            decoration.setDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.divider
                )!!
            )
            return decoration
        }
    }
}
