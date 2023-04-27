package com.dev_talk.main.tests

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev_talk.main.MainActivity
import com.dev_talk.main.screens.MainActivityScreen
import com.dev_talk.main.screens.PersonalChatsFragmentScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.dev_talk.R
import com.dev_talk.main.my_chats.PersonalChatsAdapter

//Bazhenov Kirill
@RunWith(AndroidJUnit4::class)
class PersonalChatsSearchFragmentTest {
    private lateinit var mainActivityScreen: MainActivityScreen
    private lateinit var personalChatsFragmentScreen: PersonalChatsFragmentScreen


    companion object {
        private const val QUERY = "C++"
    }

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mainActivityScreen = MainActivityScreen()
        mainActivityScreen.openPersonalChats()
        personalChatsFragmentScreen = PersonalChatsFragmentScreen()
    }

    @Test
    fun testSearchInPersonalChats() {
        personalChatsFragmentScreen.performProfessionWithIndex(0)
        personalChatsFragmentScreen.searchPersonalChat(QUERY)
        onView(withId(R.id.list_with_my_chats))
            .perform(actionOnItemAtPosition<PersonalChatsAdapter.ChatItemViewHolder>(0, click()))
    }
}