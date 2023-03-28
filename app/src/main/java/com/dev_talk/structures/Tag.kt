package com.dev_talk.structures;

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag (
    val id: Long,
    val name: String,
    var isSelected: Boolean
) : Parcelable
