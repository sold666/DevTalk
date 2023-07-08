package com.dev_talk.common.structures;

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagDto (
    val id: Long,
    val name: String,
    var isSelected: Boolean
) : Parcelable
