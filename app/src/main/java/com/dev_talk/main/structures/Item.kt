package com.dev_talk.main.structures

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val chat: Chat
) : ProfileData(), Parcelable