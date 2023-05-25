package com.dev_talk.main.tests

import com.dev_talk.main.screens.ChatFragmentScreen
import com.dev_talk.main.screens.MainActivityScreen
import org.junit.Before
import org.junit.Test

class ChatFragmentTest : BaseTest() {
    private lateinit var mainActivityScreen: MainActivityScreen
    private lateinit var chatFragmentScreen: ChatFragmentScreen

    @Before
    fun setup() {
        mainActivityScreen = MainActivityScreen()
        mainActivityScreen.openPersonalChats()
        mainActivityScreen.openFirstChat()
        chatFragmentScreen = ChatFragmentScreen()
    }

    @Test
    fun bottomNavigationTest() {
        chatFragmentScreen.openChatSettings()
        chatFragmentScreen.clickSwitchButton()
        chatFragmentScreen.checkIfSwitchButtonClickable()
    }
}
