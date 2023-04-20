package com.dev_talk.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dev_talk.R
import org.junit.Rule
import org.junit.Test

//Emelyanov Pavel
class MainActivityTest {
    @get:Rule
    var activityActivityTestRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun bottomNavigationTest() {
        onView(withId(R.id.profile_fragment_container)).perform(click())
        onView(withId(R.id.name)).check(matches(isDisplayed()))
        onView(withId(R.id.recommended_chats_fragment_container)).perform(click())
        onView(withId(R.id.recommended_chats)).check(matches(isDisplayed()))
        onView(withId(R.id.personal_chats_fragment_container)).perform(click())
        onView(withId(R.id.chats_with_category)).check(matches(isDisplayed()))
    }
}
