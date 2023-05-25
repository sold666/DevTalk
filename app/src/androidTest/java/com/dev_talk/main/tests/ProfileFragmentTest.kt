package com.dev_talk.main.tests

import com.dev_talk.main.screens.MainActivityScreen
import com.dev_talk.main.screens.ProfileFragmentScreen
import org.junit.Before
import org.junit.Test

class ProfileFragmentTest : BaseTest() {
    private lateinit var mainActivityScreen: MainActivityScreen
    private lateinit var profileFragmentScreen: ProfileFragmentScreen

    companion object {
        private const val BAR_ITEM_NAME = "Edit"
    }

    @Before
    fun setup() {
        mainActivityScreen = MainActivityScreen()
        mainActivityScreen.openPersonalChats()
        profileFragmentScreen = ProfileFragmentScreen()
    }

    @Test
    fun bottomNavigationTest() {
        profileFragmentScreen.openAppBar()
        val barItem = profileFragmentScreen.getBarItem(BAR_ITEM_NAME)
        profileFragmentScreen.checkIfBarItemIsDisplayed(barItem)
    }
}
