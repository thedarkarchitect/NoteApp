package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.data.data_source.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNote(id)
    }
}