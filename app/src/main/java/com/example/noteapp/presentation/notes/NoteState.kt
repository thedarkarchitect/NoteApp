package com.example.noteapp.presentation.notes

import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.util.NoteOrder
import com.example.noteapp.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),//will be ordered based on date in an descending
    val isOrderSectionVisible: Boolean = false
)

