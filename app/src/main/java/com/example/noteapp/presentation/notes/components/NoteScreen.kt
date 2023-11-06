package com.example.noteapp.presentation.notes.components

//import androidx.navigation.NavController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteapp.presentation.notes.NoteState
import com.example.noteapp.presentation.notes.NotesEvent
import com.example.noteapp.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: NoteState,
    onEvent: (NotesEvent) -> Unit
){
    
    val scaffoldState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    Scaffold(
        snackbarHost = { SnackbarHost(scaffoldState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          navController.navigate(Screen.AddEditNoteScreen.route)
                },
                modifier
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Note",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelLarge
                )
                IconButton(
                    onClick = {
                        onEvent(NotesEvent.ToggleOrderSection)
                    },
                ){
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange =  {
                        onEvent(NotesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = modifier.height(16.dp))
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        onDeleteClick = {
                            onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun NoteScreenPreview(){
//    NoteAppTheme {
//        NoteScreen(
//            state = NoteState(
//                notes = listOf(
//                    Note(
//                        title = "Must do",
//                        content = "saturday must drink while watching F1",
//                        timestamp = "04/11/2023".toLong(),
//                        color = RedOrange.toArgb(),
//                        id = 1
//                    )
//                )
//            ),
//            onEvent = {},
//            navController =
//        )
//    }
//}
//
