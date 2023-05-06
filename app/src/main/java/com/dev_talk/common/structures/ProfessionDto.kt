package com.dev_talk.common.structures

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfessionDto (
    val id: Long,
    val name: String,
    val tags: List<TagDto>,
    var isSelected: Boolean
) : Parcelable
