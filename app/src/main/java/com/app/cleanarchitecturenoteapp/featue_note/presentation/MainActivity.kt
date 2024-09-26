package com.app.cleanarchitecturenoteapp.featue_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.cleanarchitecturenoteapp.featue_note.presentation.add_edit_notes.AddEditNoteScreen
import com.app.cleanarchitecturenoteapp.featue_note.presentation.notes.NoteScreen
import com.app.cleanarchitecturenoteapp.featue_note.presentation.util.ObserveAsEvents
import com.app.cleanarchitecturenoteapp.featue_note.presentation.util.Screen
import com.app.cleanarchitecturenoteapp.featue_note.presentation.util.SnackbarController
import com.app.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanArchitectureNoteAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val snackbarHostState = remember {
                        SnackbarHostState()
                    }

                    val scope = rememberCoroutineScope()
                    ObserveAsEvents(
                        flow = SnackbarController.events,
                        snackbarHostState
                    ) { event ->
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()

                            val result = snackbarHostState.showSnackbar(
                                message = event.message,
                                actionLabel = event.action?.name,
                                duration = SnackbarDuration.Long
                            )

                            if(result == SnackbarResult.ActionPerformed) {
                                event.action?.action?.invoke()
                            }
                        }
                    }

                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(
                                hostState = snackbarHostState
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screen.NoteScreen.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {

                            composable(route = Screen.NoteScreen.route) {
                                NoteScreen(navController = navController)
                            }

                            composable(
                                route = Screen.AddEditNoteScreen.route
                                        + "?noteId={noteId}&noteColor={noteColor}&textData={textData}",
                                arguments = listOf(
                                    navArgument(
                                        name = "noteId"
                                    ) {
                                        type = NavType.IntType
                                        defaultValue = -1
                                    },
                                    navArgument(
                                        name = "noteColor"
                                    ) {
                                        type = NavType.IntType
                                        defaultValue = -1
                                    },
                                    navArgument(
                                        name = "textData"
                                    ) {
                                        type = NavType.StringType
                                        defaultValue = "textData"
                                    }

                                )
                            ) {
                                val color = it.arguments?.getInt("noteColor") ?: -1
                                AddEditNoteScreen(navController = navController, noteColor = color)
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CleanArchitectureNoteAppTheme {
        Greeting("Android")
    }
}