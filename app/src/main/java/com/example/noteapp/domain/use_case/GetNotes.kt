package com.example.noteapp.domain.use_case

import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.repository.NoteRepository
import com.example.noteapp.domain.util.NoteOrder
import com.example.noteapp.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending) //returns notes in descending by default
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType){
                OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    }
                }
                OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    }
                }
            }
        }
    }
}