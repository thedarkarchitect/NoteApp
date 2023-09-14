package com.example.noteapp.feature_note.data.use_case

import com.example.noteapp.feature_note.data.use_case.DeleteNote
import com.example.noteapp.feature_note.data.use_case.GetNotes

data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote
)
