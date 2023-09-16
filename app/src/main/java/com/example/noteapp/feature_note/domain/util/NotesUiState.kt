package com.example.noteapp.feature_note.domain.util

import com.example.noteapp.feature_note.data.data_source.Note
import com.example.noteapp.feature_note.data.util.NoteOrder
import com.example.noteapp.feature_note.data.util.OrderType

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSelectionVisible: Boolean = false
)

