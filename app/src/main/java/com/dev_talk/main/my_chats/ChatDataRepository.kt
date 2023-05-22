package com.dev_talk.main.my_chats

import androidx.lifecycle.MutableLiveData
import com.dev_talk.main.structures.Profession

object ChatDataRepository {
    val chatData: MutableLiveData<List<Profession>> = MutableLiveData()

    fun updateChatData(newChatData: List<Profession>) {
        chatData.value = newChatData
    }
}