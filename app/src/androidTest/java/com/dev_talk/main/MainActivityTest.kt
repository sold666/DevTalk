package com.dev_talk.main

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.dev_talk.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//Emelyanov Pavel
class MainActivityTest {
    @Rule
    var activityActivityTestRule = ActivityTestRule(
        MainActivity::class.java
    )

    @Test
    fun clickButtonHome() {
        onView(withId(R.id.profile_fragment_container)).perform(click())
        onView(withId(R.id.name)).check(matches(isDisplayed()))
    }
}