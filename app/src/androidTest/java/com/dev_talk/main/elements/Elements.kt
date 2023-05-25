package com.dev_talk.main.elements

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.ViewInteraction
import com.dev_talk.R

val Elements: List<ViewInteraction> = listOf(
    onView(withId(R.id.switch_button))
)
