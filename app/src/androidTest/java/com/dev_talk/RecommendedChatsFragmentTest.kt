package com.dev_talk

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.dev_talk.main.recommended_chats.RecommendedChatsFragment


@RunWith(AndroidJUnit4::class)
class RecommendedChatsFragmentTest : TestCase() {
    private lateinit var scenario: FragmentScenario<RecommendedChatsFragment>

    @Before
    override fun setUp() {
        scenario = launchFragmentInContainer()
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testSearchInRecommendations() {
        onView(withId(R.id.menu_search)).perform(click())
        assertTrue(true)
    }

}
