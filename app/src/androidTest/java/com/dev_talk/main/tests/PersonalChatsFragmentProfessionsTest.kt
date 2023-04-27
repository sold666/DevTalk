package com.dev_talk.main.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev_talk.main.MainActivity
import com.dev_talk.main.screens.MainActivityScreen
import com.dev_talk.main.screens.PersonalChatsFragmentScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//Bazhenov Kirill
@RunWith(AndroidJUnit4::class)
class PersonalChatsFragmentProfessionsTest {

    private lateinit var mainActivityScreen: MainActivityScreen
    private lateinit var personalChatsFragmentScreen: PersonalChatsFragmentScreen

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mainActivityScreen = MainActivityScreen()
        mainActivityScreen.openPersonalChats()
        personalChatsFragmentScreen = PersonalChatsFragmentScreen()
    }

    @Test
    fun checkWorkingTabs() {
        for (i in 0..2) {
            personalChatsFragmentScreen.performProfessionWithIndex(i)
        }
    }

}