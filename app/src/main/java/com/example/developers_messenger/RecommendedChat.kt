package com.example.developers_messenger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RecommendedChat(
    val icon: Int,
    val tags: String
) : Parcelable
