package com.example.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.presentation.add_edit_note.components.AddEditNoteEvent
import com.example.noteapp.presentation.notes.NotesViewModel
import com.example.noteapp.presentation.notes.components.NoteScreen
import com.example.noteapp.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteScreen.route
                    ){
                        composable(route = Screen.NoteScreen.route) {
                            val noteScreenViewModel = hiltViewModel<NotesViewModel>()
                            NoteScreen(
                                state = noteScreenViewModel.state.value,
                                onEvent = noteScreenViewModel::onEvent,
                                navController = navController
                            )
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(name = "noteId"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "noteColor"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            val color = it.arguments?.getInt("noteColor") ?: -1 //it stands for the NavBackStackEntry values
                            AddEditNoteEvent(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
