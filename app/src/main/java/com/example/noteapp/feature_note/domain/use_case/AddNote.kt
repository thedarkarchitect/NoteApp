package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.data.data_source.InvalidNoteException
import com.example.noteapp.feature_note.data.data_source.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty.")
        }

        if(note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty.")
        }

        repository.insertNote(note)
    }
}