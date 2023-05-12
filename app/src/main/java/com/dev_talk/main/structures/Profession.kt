package com.dev_talk.main.structures

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profession(
    val profession: String,
    var chats: MutableList<Chat>
) : Parcelable
