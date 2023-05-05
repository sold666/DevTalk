package com.dev_talk.main.structures

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(
    @DrawableRes
    val icon: Int = 0,
    val tags: String = "",
    val lastMessage: String = "",
    val memberCount: String = ""
) : Parcelable
