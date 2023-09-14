package com.example.noteapp.feature_note.data.use_case

import com.example.noteapp.feature_note.data.data_source.Note
import com.example.noteapp.feature_note.data.repository.NoteRepository
import com.example.noteapp.feature_note.data.util.NoteOrder
import com.example.noteapp.feature_note.data.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>>{
        return repository.getAllNotes().map{notes ->
            when(noteOrder.orderType){
                is OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedBy{ it.title.lowercase()}
                        is NoteOrder.Date -> notes.sortedBy{ it.timestamp}
                        is NoteOrder.Color -> notes.sortedBy{ it.color}
                    }
                }

                is OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> notes.sortedByDescending{ it.title.lowercase()}
                        is NoteOrder.Date -> notes.sortedByDescending{ it.timestamp}
                        is NoteOrder.Color -> notes.sortedByDescending{ it.color}
                    }
                }
            }
        }
    }
}