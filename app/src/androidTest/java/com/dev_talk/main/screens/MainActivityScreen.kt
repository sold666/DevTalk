package com.dev_talk.main.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dev_talk.R

class MainActivityScreen() {
    init {
        check()
    }

    private fun check() {
        onView(withId(R.id.main_bottom_nav_view))
            .check(matches(isDisplayed()))
    }

    fun openProfile() {
        onView(withId(R.id.profile_fragment_container)).perform(click())
    }

    fun openRecommendedChats() {
        onView(withId(R.id.recommended_chats_fragment_container)).perform(click())
    }

    fun openPersonalChats() {
        onView(withId(R.id.personal_chats_fragment_container)).perform(click())
    }
}