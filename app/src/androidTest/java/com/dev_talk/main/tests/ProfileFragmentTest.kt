package com.dev_talk.main.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dev_talk.main.MainActivity
import com.dev_talk.main.screens.MainActivityScreen
import com.dev_talk.main.screens.ProfileFragmentScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileFragmentTest {
    private lateinit var mainActivityScreen: MainActivityScreen
    private lateinit var profileFragmentScreen: ProfileFragmentScreen

    companion object {
        private const val BAR_ITEM = "Edit"
    }

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        mainActivityScreen = MainActivityScreen()
        mainActivityScreen.openPersonalChats()
        profileFragmentScreen = ProfileFragmentScreen()
    }

    @Test
    fun bottomNavigationTest() {
        profileFragmentScreen.openAppBar()
        onView(withSubstring(BAR_ITEM)).check(matches(isDisplayed()))
    }
}
