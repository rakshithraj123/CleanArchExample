package com.app.cleanarchitecturenoteapp.featue_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextInputSelection
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.cleanarchitecturenoteapp.core.TestTag
import com.app.cleanarchitecturenoteapp.di.AppModule
import com.app.cleanarchitecturenoteapp.featue_note.presentation.add_edit_notes.AddEditNoteScreen
import com.app.cleanarchitecturenoteapp.featue_note.presentation.notes.NoteScreen
import com.app.cleanarchitecturenoteapp.featue_note.presentation.util.Screen
import com.app.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteEndToEndTest {

    @get:Rule(order = 0)
    val hitRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


//    @Before
//    fun setUp() {
//        hitRule.inject()
//        composeRule.activity.setContent {
//            CleanArchitectureNoteAppTheme {
//                Surface(
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val  navController = rememberNavController()
//                    NavHost(
//                        navController = navController,
//                        startDestination = Screen.NoteScreen.route){
//
//                        composable(route = Screen.NoteScreen.route){
//                            NoteScreen(navController = navController)
//                        }
//
//                        composable(
//                            route = Screen.AddEditNoteScreen.route
//                                    + "?noteId={noteId}&noteColor={noteColor}&textData={textData}",
//                            arguments = listOf(
//                                navArgument(
//                                    name = "noteId"
//                                ){
//                                    type = NavType.IntType
//                                    defaultValue = -1
//                                },
//                                navArgument(
//                                    name = "noteColor"
//                                ){
//                                    type = NavType.IntType
//                                    defaultValue = -1
//                                },
//                                navArgument(
//                                    name = "textData"
//                                ){
//                                    type = NavType.StringType
//                                    defaultValue = "textData"
//                                }
//
//                            )
//                        ){
//                            val color = it.arguments?.getInt("noteColor")?: -1
//                            AddEditNoteScreen(navController = navController, noteColor = color)
//                        }
//                    }
//                }
//            }
//        }
//    }


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun addTextAtEndInBasicTextField() {
        // Setup state for text input
        var text by mutableStateOf("existing text")
        val textStyle = TextStyle()

        // Set up your Compose UI with BasicTextField
        composeRule.activity.setContent {
            BasicTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                textStyle = textStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { /* Handle focus change if needed */ }
                    .testTag("myTextField")
            )
        }

        composeRule.onNodeWithTag("myTextField").performTextInputSelection(TextRange("existing text".length))

        // Find the BasicTextField using the tag and perform text input
        composeRule.onNodeWithTag("myTextField")
            .performTextInput(" appended text")

        //composeRule.onNodeWithTag("myTextField").performTextInputSelection()

        // Assert that the new content of the BasicTextField is as expected
        composeRule.onNodeWithTag("myTextField")
            .assert(hasText("existing text appended text"))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun saveNewNote_editAfterwards(){
        composeRule.onNodeWithContentDescription("Add").performClick()

        composeRule.onRoot(useUnmergedTree = true).printToLog("**clickToggleOrderSection**")

        composeRule.onNodeWithTag(TestTag.TITLE_TEXT_FIELD).performTextInput("Title-text")
        composeRule.onNodeWithTag(TestTag.CONTENT_TEXT_FIELD).performTextInput("Title-content")
        composeRule.onNodeWithContentDescription("Save").performClick()

        composeRule.onNodeWithText("Title-text").assertIsDisplayed()
        composeRule.onNodeWithText("Title-text").performClick()

        composeRule.onNodeWithTag(TestTag.TITLE_TEXT_FIELD).assertTextEquals("Title-text")
        composeRule.onNodeWithTag(TestTag.CONTENT_TEXT_FIELD).assertTextEquals("Title-content")
        composeRule.onNodeWithTag(TestTag.TITLE_TEXT_FIELD).performTextInputSelection(TextRange("Title-text".length))
        composeRule.onNodeWithTag(TestTag.TITLE_TEXT_FIELD).performTextInput("2")
        composeRule.onNodeWithContentDescription("Save").performClick()

        composeRule.onNodeWithText("Title-text2").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun saveNewNote_orderedByTitleDescending(){
         for(i in 1..3){
             composeRule.onNodeWithContentDescription("Add").performClick()

             composeRule.onRoot(useUnmergedTree = true).printToLog("**clickToggleOrderSection**")

             composeRule.onNodeWithTag(TestTag.TITLE_TEXT_FIELD).performTextInput(i.toString())
             composeRule.onNodeWithTag(TestTag.CONTENT_TEXT_FIELD).performTextInput(i.toString())
             composeRule.onNodeWithContentDescription("Save").performClick()
         }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("sort").performClick()
        composeRule.onNodeWithContentDescription("Title").performClick()
        composeRule.onNodeWithContentDescription("Descending").performClick()

        composeRule.onAllNodesWithTag(TestTag.NOTE_ITEM)[0].assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTag.NOTE_ITEM)[1].assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTag.NOTE_ITEM)[2].assertTextContains("1")
    }
}