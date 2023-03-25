package com.dev_talk.structures

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profession (
    val id: Long,
    val name: String,
    val tags: List<String>,
    var isSelected: Boolean
) : Parcelable
