package com.dev_talk.main.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dev_talk.main.MainActivity
import org.junit.Rule

open class BaseTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)
}
