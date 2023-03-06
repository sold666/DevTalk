package com.dev_talk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profession (
    val profession: String,
    val chats: ArrayList<Chat>
) : Parcelable
