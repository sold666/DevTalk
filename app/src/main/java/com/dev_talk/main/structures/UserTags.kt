package com.dev_talk.main.structures

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserTags(
    @DrawableRes
    val icon: Int = 0,
    val tag: String = ""
) : Parcelable
