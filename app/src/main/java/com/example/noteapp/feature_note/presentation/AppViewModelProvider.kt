package com.example.noteapp.feature_note.presentation

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.noteapp.NoteApplication
import com.example.noteapp.feature_note.presentation.notes.NotesViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        //Initializer for the NotesViewModel
        //pass in the repository instance as a parameter
        initializer {
            NotesViewModel(
                NoteApplication().conatiner.noteRepository
            )
        }
    }
}