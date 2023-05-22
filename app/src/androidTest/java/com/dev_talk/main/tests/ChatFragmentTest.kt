package com.dev_talk.main.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dev_talk.R
import com.dev_talk.main.MainActivity
import com.dev_talk.main.screens.ChatFragmentScreen
import com.dev_talk.main.screens.MainActivityScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChatFragmentTest {
    private lateinit var mainActivityScreen: MainActivityScreen
    private lateinit var chatFragmentScreen: ChatFragmentScreen

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

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
        onView(withId(R.id.switch_button)).perform(click())
    }
}
