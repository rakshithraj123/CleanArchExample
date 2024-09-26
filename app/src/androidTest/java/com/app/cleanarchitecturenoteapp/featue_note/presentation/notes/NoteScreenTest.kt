package com.app.cleanarchitecturenoteapp.featue_note.presentation.notes

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.app.cleanarchitecturenoteapp.core.TestTag
import com.app.cleanarchitecturenoteapp.di.AppModule
import com.app.cleanarchitecturenoteapp.featue_note.presentation.MainActivity
import com.app.cleanarchitecturenoteapp.featue_note.presentation.util.Screen
import com.app.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteScreenTest {

    @get:Rule(order = 0)
    val hitRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hitRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            CleanArchitectureNoteAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NoteScreen.route
                ) {

                    composable(route = Screen.NoteScreen.route) {
                        NoteScreen(navController = navController)
                    }
                }
            }

        }
    }

    @Test
    fun clickToggleOrderSection_isVisible(): Unit = runBlocking {
        val context =  ApplicationProvider.getApplicationContext<Context>()

        composeRule.onNodeWithTag(TestTag.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("sort").performClick()
       // composeRule.onRoot(useUnmergedTree = true).printToLog("**clickToggleOrderSection**")
        composeRule.onNodeWithTag(TestTag.ORDER_SECTION).assertIsDisplayed()
    }
}