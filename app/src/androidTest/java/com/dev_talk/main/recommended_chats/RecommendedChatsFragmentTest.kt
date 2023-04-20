package com.dev_talk.main.recommended_chats

import android.widget.AutoCompleteTextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev_talk.R
import com.dev_talk.main.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//Emelyanov Pavel
@RunWith(AndroidJUnit4::class)
class RecommendedChatsFragmentTest {
    companion object {
        private const val QUERY = "py"
    }

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        onView(withId(R.id.recommended_chats_fragment_container)).perform(click())
        onView(withId(R.id.recommended_chats)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchInRecommendations() {
        onView(withId(R.id.menu_search)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText(QUERY))
        onView(withSubstring(QUERY)).check(matches(isDisplayed()))
    }
}

