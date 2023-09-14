package com.example.noteapp.feature_note.data.use_case

import com.example.noteapp.feature_note.data.data_source.Note
import com.example.noteapp.feature_note.data.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}