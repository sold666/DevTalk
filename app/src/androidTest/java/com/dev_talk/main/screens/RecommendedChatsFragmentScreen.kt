package com.dev_talk.main.screens

import android.widget.AutoCompleteTextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dev_talk.R

class RecommendedChatsFragmentScreen() {
    init {
        check()
    }

    private fun check() {
        onView(withId(R.id.recommended_chats))
            .check(matches(isDisplayed()))
    }

    fun searchChat(query: String) {
        Espresso.onView(ViewMatchers.withId(R.id.menu_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(ViewActions.typeText(query))
    }
}