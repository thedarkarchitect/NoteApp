package com.example.noteapp.feature_note.presentation.notes

import com.example.noteapp.feature_note.data.data_source.Note
import com.example.noteapp.feature_note.data.util.NoteOrder

//events to be performed by user tracked
sealed class NotesEvent{
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSelection:  NotesEvent()
}
