package com.example.developers_messenger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat (
    val icon: Int,
    val tags: String
) : Parcelable
