package com.dev_talk.main.screens

import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.core.view.size
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dev_talk.R
import com.google.android.material.tabs.TabLayout
import junit.framework.TestCase.assertEquals
import org.hamcrest.Matcher
import org.hamcrest.Matchers.any
import java.util.EnumSet.allOf
import kotlin.math.log

//Bazhenov Kirill
class PersonalChatsFragmentScreen() {

    init {
        check()
    }

    private fun check() {
        onView(withId(R.id.professions))
            .check(matches(isDisplayed()))
    }

    public fun performProfessionWithIndex(index: Int) {
        onView(withId(R.id.professions)).perform(selectTabAtPosition(index))
    }

    public fun searchPersonalChat(query: String) {
        Espresso.onView(ViewMatchers.withId(R.id.menu_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(ViewActions.typeText(query))
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"
            override fun getConstraints(): Matcher<View> {
                return any(View::class.java)
            }

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()
                tabAtIndex.select()
            }
        }
    }
}
