package com.example.noteapp.presentation.add_edit_note.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteapp.domain.model.Note
import com.example.noteapp.presentation.add_edit_note.AddEditNoteEvent
import com.example.noteapp.presentation.add_edit_note.AddEditNoteViewModel
import com.example.noteapp.presentation.add_edit_note.UiEvent
import com.example.noteapp.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteEvent(
    modifier: Modifier = Modifier,
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
){
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scaffoldState = remember { SnackbarHostState() }

    val noteBackgroundAnimated = remember {
        Animatable(
            Color(if(noteColor != -1) noteColor else viewModel.noteColor.intValue)
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { uiEvent ->
            when(uiEvent){
                UiEvent.SaveNote -> {

                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.showSnackbar(
                        message = uiEvent.message
                    )
                }

            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(scaffoldState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    navController.navigate(Screen.NoteScreen.route)
                },
                Modifier
                    .clip(CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        }
    ){ paddingValues ->
        Column(
            modifier
                .fillMaxSize()
                .background(noteBackgroundAnimated.value)
                .padding(16.dp)
        ){
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.intValue == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimated.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = modifier.height(8.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                                viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintvisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
            Spacer(modifier = modifier.height(12.dp))
//            TransparentHintTextField(
//                text = contentState.text,
//                hint = contentState.hint,
//                onValueChange = {
//                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
//                },
//                onFocusChange = {
//                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
//                },
//                isHintvisible = titleState.isHintVisible,
//                singleLine = true,
//                textStyle = MaterialTheme.typography.bodyLarge,
//                maxLines = 10
//            )
            Column(
                modifier = modifier
            ){
                BasicTextField(
                    value = contentState.text,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    maxLines = 10,
                    modifier = modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                        }
                )
                if(contentState.isHintVisible){
                    Text(
                        text = contentState.hint,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}