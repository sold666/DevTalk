package com.dev_talk

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev_talk.main.recommended_chats.RecommendedChatsFragment
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//Emelyanov Pavel
@RunWith(AndroidJUnit4::class)
class RecommendedChatsFragmentTest : TestCase() {
    companion object {
        private const val quary = "py"
    }

    private lateinit var scenario: FragmentScenario<RecommendedChatsFragment>

    @Before
    override fun setUp() {
        scenario = launchFragmentInContainer()
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testSearchInRecommendations() {
        onView(withId(R.id.menu_search)).perform(click()).perform(typeText(quary))
        onView(withSubstring(quary)).check(matches(isDisplayed()))


    }

}
