package com.dev_talk.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dev_talk.R
import com.dev_talk.main.screens.MainActivityScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//Emelyanov Pavel
class MainActivityTest {
    private lateinit var mainActivityScreen: MainActivityScreen

    @get:Rule
    var activityActivityTestRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Before
    fun setup() {
        mainActivityScreen = MainActivityScreen()
    }

    @Test
    fun bottomNavigationTest() {
        mainActivityScreen.openProfile()
        onView(withId(R.id.name)).check(matches(isDisplayed()))
        mainActivityScreen.openRecommendedChats()
        onView(withId(R.id.recommended_chats)).check(matches(isDisplayed()))
        mainActivityScreen.openPersonalChats()
        onView(withId(R.id.chats_with_category)).check(matches(isDisplayed()))
    }
}
