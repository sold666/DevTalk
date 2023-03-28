package com.dev_talk.auth.structures;

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag (
    val id: Long,
    val name: String,
    var isSelected: Boolean
) : Parcelable
