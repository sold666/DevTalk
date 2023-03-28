package com.dev_talk.chat

import androidx.annotation.DrawableRes

data class ChatParticipant(
    @DrawableRes
    val icon: Int,
    val name: String,
    val status: String
)
