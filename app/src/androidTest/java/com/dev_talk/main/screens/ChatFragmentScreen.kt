package com.dev_talk.main.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dev_talk.R

class ChatFragmentScreen() {
    init {
        check()
    }

    private fun check() {
        onView(withId(R.id.chat_field))
            .check(matches(isDisplayed()))
    }

    fun openChatSettings() {
        onView(withId(R.id.chat_app_bar)).perform(click())
    }
}
