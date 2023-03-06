package com.example.developers_messenger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profession (
    val profession: String,
    val chats: ArrayList<Chat>
) : Parcelable
